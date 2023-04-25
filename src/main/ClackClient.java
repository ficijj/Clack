package main;

import GUI.MessageBuffer;
import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Scanner;

public class ClackClient implements Runnable {
    private MessageBuffer buffer;

    private String username;
    private String hostName;
    private String key;
    private int port;
    private boolean closeConnection;

    private Scanner inFromStd;

    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;
    private ClackData dataToSendToServer;
    private ClackData dataToReceiveFromServer;

    private ClientSideServerListener listener;

    private static final SecureRandom RAND = new SecureRandom();

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    private String desKey;

    Socket skt;
    private static int DEFAULT_PORT = 7000;

    /**
     * Constructor that sets instance variables to passed values or defaults, where applicable
     *
     * @param username Name of the user
     * @param hostName Name of the host
     * @param port     Number to be used for connection to the server
     */
    public ClackClient(String username, String hostName, int port, MessageBuffer buffer) throws IllegalArgumentException {
        if (username == null) {
            throw new IllegalArgumentException("Username is null");
        } else {
            this.username = username;
        }
        if (hostName == null) {
            throw new IllegalArgumentException("Host name is null");
        } else {
            this.hostName = hostName;
        }
        if (port < 1024) {
            throw new IllegalArgumentException("Port number is to low");
        } else {
            this.port = port;
        }
        this.buffer = buffer;
        closeConnection = false;
        inFromServer = null;
        outToServer = null;
        dataToSendToServer = null;
        dataToReceiveFromServer = null;

        listener = null;

        skt = null;
    }

    /**
     * Constructor that sets user and host names to provided values and the rest to default values
     *
     * @param username Name of the user
     * @param hostName Name of the host
     */
    public ClackClient(String username, String hostName, MessageBuffer buffer) {
        this(username, hostName, DEFAULT_PORT, buffer);
    }

    /**
     * Constructor that sets username to provided value and the rest to default values
     *
     * @param username Name of the user
     */
    public ClackClient(String username, MessageBuffer buffer) {
        this(username, "localhost", buffer);
    }

    /**
     * Constructor that sets all instance variables to their default values
     */
    public ClackClient(MessageBuffer buffer) {
        this("Anon", buffer);
    }

    /**
     * Starts the client and continues to read input from stdin and print it as long as the connection remains open
     */
    public void run() {
        inFromStd = new Scanner(System.in);
        try {
            skt = new Socket(hostName, DEFAULT_PORT);

            outToServer = new ObjectOutputStream(skt.getOutputStream());
            inFromServer = new ObjectInputStream(skt.getInputStream());

            Thread l = new Thread(new ClientSideServerListener(this, buffer));
            l.start();

            sendRSAKeys();
            receiveDESKey();
            sendUsername();
            while (!buffer.isCloseConnection()) {
                dataToSendToServer = buffer.readOutgoingMessage();
                System.out.println("sending : " + dataToSendToServer);
                sendData();
            }
            System.out.println("Closing connections...");
            inFromStd.close();
            skt.close();
            outToServer.close();
            inFromServer.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void sendRSAKeys() {
        generateRSAKeys(4000);
        System.out.println("Sending RSA keys...");
        try {
            String keys = "n: " + n + "\ne: " + e;
            outToServer.writeObject(keys);
            outToServer.flush();
            System.out.println("Data flushed...");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    private void generateRSAKeys(int bitLength) {
        System.out.println("Generating RSA keys...");
        p = BigInteger.probablePrime(bitLength, RAND);
        q = BigInteger.probablePrime(bitLength, RAND);

        n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitLength / 2, RAND);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public void receiveDESKey() {
        System.out.println("Receiving DES key...");
        try {
            String temp = (String) inFromServer.readObject();
            String[] encryptedValues = temp.split(" ");
            for (String encryptedValue : encryptedValues) {
                BigInteger decrypted = new BigInteger(encryptedValue).modPow(d, n);
                desKey += (char) decrypted.intValue();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Sends a string object containing only the clients' username to be stored by the server
     */
    public void sendUsername() {
        System.out.println("Sending username...");
        try {
            outToServer.writeObject(username);
            outToServer.flush();
            System.out.println("Data flushed...");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        try {
            outToServer.writeObject(new MessageClackData(username, null, ClackData.CONST_LIST_USERS));
            outToServer.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Sends the data read in from stdin to the server
     */
    public void sendData() {
        System.out.println("Sending data...");
        try {
            outToServer.writeObject(dataToSendToServer);
            outToServer.flush();
            System.out.println("Data flushed...");
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Receives any data sent by the server
     */
    public void receiveData() {
        try {
            System.out.println("Receiving data...");
            dataToReceiveFromServer = (ClackData) inFromServer.readObject();
            System.out.println("username: " + dataToReceiveFromServer.getUsername());
            if(dataToReceiveFromServer.getUsername().equals("server")){
                System.out.println("getting user list...");
                buffer.getUsersOList().add((String) dataToReceiveFromServer.getData());
            } else {
                System.out.println("getting message...");
                buffer.getMessageOList().add(dataToReceiveFromServer.getUsername() + ": " + dataToReceiveFromServer.getData() + '\n');
            }
//            System.out.println("Received data: " + dataToReceiveFromServer);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Prints the data from dataToReceiveFromServer to stdout
     */
    public void printData() {
        System.out.println(dataToReceiveFromServer.getUsername() + ": " + dataToReceiveFromServer.getData());
    }

    /**
     * Accessor for the username
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Accessor for the host name
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Accessor for the port
     *
     * @return the port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Tells you whether the connection is closed
     *
     * @return the connection status
     */
    public boolean isCloseConnection() {
        return closeConnection;
    }

    /**
     * Sets the close connection to the value of the parameter
     *
     * @param closeConnection value to set close connection to
     */
    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * username.hashCode();
        result = 37 * hostName.hashCode();
        result = 37 * Integer.hashCode(port);
        result = 37 * Boolean.hashCode(closeConnection);
        result = 37 * dataToSendToServer.hashCode();
        result = 37 * dataToReceiveFromServer.hashCode();
        return result;
    }

    /**
     * Tests if two instances of ClackClient are identical
     *
     * @param comp the class to compare to
     * @return true if exactly equal, false otherwise
     */
    public boolean equals(ClackClient comp) {
        if (comp == null) return false;
        if (!(comp instanceof ClackClient)) return false;
        return this.username.equals(comp.username) &&
                this.hostName.equals(comp.hostName) &&
                this.port == comp.port &&
                this.closeConnection == comp.closeConnection &&
                this.dataToSendToServer.equals(comp.dataToSendToServer) &&
                this.dataToReceiveFromServer.equals(comp.dataToReceiveFromServer);
    }

    @Override
    public String toString() {
        return "ClackClient{" +
                "username='" + username + '\'' +
                ", hostName='" + hostName + '\'' +
                ", port=" + port +
                ", closeConnection=" + closeConnection +
                ", dataToSendToServer=" + dataToSendToServer +
                ", dataToReceiveFromServer=" + dataToReceiveFromServer +
                '}';
    }

}