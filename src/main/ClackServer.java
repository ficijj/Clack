package main;

import data.ClackData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class ClackServer {
    private int port;

    ObjectInputStream inFromClient;
    ObjectOutputStream outToClient;
    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;

    ServerSocket sskt;
    private static final int DEFAULT_PORT = 7000;

    private boolean closeConnection;

    /**
     * Constructor that sets the port number to the provided value and all other instance variables to their defaults
     *
     * @param port - port connection number
     */
    public ClackServer(int port) throws IllegalArgumentException {
        if (port < 1024) {
            throw new IllegalArgumentException("Port number is to low");
        } else {
            this.port = port;
        }

        inFromClient = null;
        outToClient = null;
        dataToReceiveFromClient = null;
        dataToSendToClient = null;
        sskt = null;
    }

    /**
     * Constructor that sets all instance variables to their defaults
     */
    public ClackServer() {
        this(DEFAULT_PORT);
    }

    //methods

    /**
     * Accessor for the port
     *
     * @return the port number
     */
    public int getPort() {
        return port;
    }

    public void start() {
        try {
            sskt = new ServerSocket(port);

            outToClient = new ObjectOutputStream(sskt.accept().getOutputStream());
            inFromClient = new ObjectInputStream(sskt.accept().getInputStream());

            while (!closeConnection) {
                receiveData();
                sendData();
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void receiveData() {
        try {
            dataToReceiveFromClient = (ClackData) inFromClient.readObject();
            if (dataToReceiveFromClient == null) {
                closeConnection = true;
                System.out.println("Connection closing...");
            } else {
                System.out.println("Received data: " + dataToReceiveFromClient);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendData() {
        try {
            outToClient.writeObject(dataToSendToClient);
            outToClient.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * Integer.hashCode(port);
        result = 37 * dataToReceiveFromClient.hashCode();
        result = 37 * dataToSendToClient.hashCode();
        result = 37 * Boolean.hashCode(closeConnection);
        return result;
    }

    /**
     * Tests if two instances of ClackServer are identical
     *
     * @param comp the class to compare to
     * @return true if exactly equal, false otherwise
     */
    public boolean equals(ClackServer comp) {
        if (comp == null) return false;
        if (!(comp instanceof ClackServer)) return false;
        return this.port == comp.port &&
                this.dataToReceiveFromClient.equals(comp.dataToReceiveFromClient) &&
                this.dataToSendToClient.equals(comp.dataToSendToClient);
    }

    @Override
    public String toString() {
        return "ClackClient{" +
                "port=" + port +
                ", closeConnection=" + closeConnection +
                ", dataToSendToClient=" + dataToSendToClient +
                ", dataToReceiveFromClient=" + dataToReceiveFromClient +
                '}';
    }

    public static void main(String[] args) {
        ClackServer s = null;
        if (args.length < 1) {
            s = new ClackServer();
        } else {
            s = new ClackServer(Integer.parseInt(args[0]));
        }
    }

}
