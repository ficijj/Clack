package main;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClackClient {
    private String username;
    private String hostName;
    private String key;
    private int port;
    private boolean closeConnection;

    private Scanner inFromStd;

    private ObjectOutputStream inFromServer;
    private ObjectInputStream outToServer;
    private ClackData dataToSendToServer;
    private ClackData dataToReceiveFromServer;
    private ServerSocket sskt;

    private static int DEFAULT_PORT = 7000;

    /**
     * Constructor that sets instance variables to passed values or defaults, where applicable
     *
     * @param username Name of the user
     * @param hostName Name of the host
     * @param port     Number to be used for connection to the server
     */
    public ClackClient(String username, String hostName, int port) throws IllegalArgumentException {
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
        closeConnection = false;
        inFromServer = null;
        outToServer = null;
        dataToSendToServer = null;
        dataToReceiveFromServer = null;
        sskt = null;
    }

    /**
     * Constructor that sets user and host names to provided values and the rest to default values
     *
     * @param username Name of the user
     * @param hostName Name of the host
     */
    public ClackClient(String username, String hostName) {
        this(username, hostName, DEFAULT_PORT);
    }

    /**
     * Constructor that sets username to provided value and the rest to default values
     *
     * @param username Name of the user
     */
    public ClackClient(String username) {
        this(username, "localhost");
    }

    /**
     * Constructor that sets all instance variables to their default values
     */
    public ClackClient() {
        this("Anon");
    }

    /**
     * Starts the client and continues to read input from stdin and print it as long as the connection remains open
     */
    public void start() {
        inFromStd = new Scanner(System.in);
        try {
            String serverName = "";
            Socket skt = new Socket(serverName, port);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        while (!closeConnection) {
            readClientData();
            printData();
        }
    }

    /**
     * Reads the data from stdin and takes the corresponding actions
     */
    public void readClientData() {
        String input = inFromStd.next();
        if (input.equals("DONE")) {
            closeConnection = true;
        } else if (input.substring(0, 7).equals("SENDFILE")) {
            dataToSendToServer = new FileClackData("Anon", input.substring(9), ClackData.CONST_SEND_FILE);
            try {
                ((FileClackData) dataToSendToServer).readFileContents();
            } catch (IOException e) {
                dataToSendToServer = null;
                System.err.println("File not able to be read. ");
            }
        } else if (input.substring(0, 8).equals("LISTUSERS")) {

        } else {
            dataToSendToServer = new MessageClackData("Anon", input, ClackData.CONST_SEND_MESSAGE);
        }
    }

    public void sendData() {

    }

    public void receiveData() {

    }

    /**
     * Prints the data from dataToReceiveFromServer to stdout
     */
    public void printData() {
        System.out.println(dataToReceiveFromServer.getData());
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