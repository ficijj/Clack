package main;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClackClient {
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

    Socket skt;
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
        skt = null;
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
            String serverName = "localhost";
            skt = new Socket(serverName, DEFAULT_PORT);

            outToServer = new ObjectOutputStream(skt.getOutputStream());
            inFromServer = new ObjectInputStream(skt.getInputStream());
            while (!closeConnection) {
                readClientData();
                sendData();
                receiveData();
                if(dataToReceiveFromServer != null) {
                    System.out.println("Printing data...");
                    printData();
                }
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

    /**
     * Reads the data from stdin and takes the corresponding actions
     */
    public void readClientData() {
        System.out.println("Reading client data...");
        String input = inFromStd.nextLine();
        if (input.equals("DONE")) {
            closeConnection = true;
            dataToSendToServer = null;
        } else if (input.length() > 7 && input.substring(0, 7).equals("SENDFILE")) {
            dataToSendToServer = new FileClackData("Anon", input.substring(9), ClackData.CONST_SEND_FILE);
            try {
                ((FileClackData) dataToSendToServer).readFileContents();
            } catch (IOException e) {
                dataToSendToServer = null;
                System.err.println("File not able to be read. ");
            }
        } else if (input.length() > 8 && input.substring(0, 8).equals("LISTUSERS")) {

        } else {
            dataToSendToServer = new MessageClackData("Anon", input, ClackData.CONST_SEND_MESSAGE);
            System.out.println("Data to be sent: " + dataToSendToServer);
        }
    }

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

    public void receiveData() {
        try {
            System.out.println("Receiving data...");
            dataToReceiveFromServer = (ClackData) inFromServer.readObject();
            System.out.println("Received data: " + dataToReceiveFromServer);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
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

    public static void main(String[] args) {
        int port = 0;
        String user = null;
        String host = null;
        String cmdArgs = "";
        ClackClient c = null;

        int path = 0;
        if (args.length != 0) {
            path = determineCase(args[0]);
        }

        switch (path) {
            case 0: //no args
                c = new ClackClient();
                break;
            case 1: //just username
                c = new ClackClient(cmdArgs);
                break;
            case 2: //user and host name
                user = cmdArgs.split("@")[0];
                host = cmdArgs.split("@")[1];
                c = new ClackClient(user, host);
                break;
            case 3: //username, hostname, and port number
                user = cmdArgs.split("@")[0];
                host = (cmdArgs.split("@")[1]).split(":")[0];
                port = Integer.parseInt((cmdArgs.split("@")[1]).split(":")[1]);
                c = new ClackClient(user, host, port);
                break;
        }
        System.out.println(c);
        c.start();
    }

    private static int determineCase(String args) {
        if (!args.contains("@") && !args.contains(":")) {
            return 1;
        } else if (args.contains("@") && !args.contains(":")) {
            return 2;
        } else if (args.contains("@") && args.contains(":")) {
            return 3;
        } else {
            return 0;
        }
    }

}