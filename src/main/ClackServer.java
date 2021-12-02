package main;

import data.ClackData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClackServer {
    private int port;

    ServerSocket sskt;
    Socket skt;
    private static final int DEFAULT_PORT = 7000;

    private boolean closeConnection;

    ArrayList<ServerSideClientIO> serverSideClientIOList;
    ArrayList<String> usernames;

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
        sskt = null;
        skt = null;
        closeConnection = false;
        serverSideClientIOList = new ArrayList<ServerSideClientIO>();
        usernames = new ArrayList<String>();
    }

    /**
     * Constructor that sets all instance variables to their defaults
     */
    public ClackServer() {
        this(DEFAULT_PORT);
    }

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
            sskt = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server started, waiting for connections... ");
            while(!closeConnection) {
                serverSideClientIOList.add(new ServerSideClientIO(this, sskt.accept()));
                Thread l = new Thread(serverSideClientIOList.get(serverSideClientIOList.size() - 1));
                l.start();
            }
            sskt.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Sends out the recently received data to all available clients
     * @param dataToBroadcastToClients the data to be sent out
     */
    public synchronized void broadcast(ClackData dataToBroadcastToClients){
        System.out.println("Broadcasting to: " + serverSideClientIOList);
        for (ServerSideClientIO e : serverSideClientIOList) {
            e.setDataToSendToClient(dataToBroadcastToClients);
            e.sendData();
        }
    }

    /**
     * Closes a single clients' connection to the server and removes its username from the list
     * @param serverSideClientToRemove the specific client to be removed
     */
    public synchronized void remove(ServerSideClientIO serverSideClientToRemove){
        int index = serverSideClientIOList.indexOf(serverSideClientToRemove);
        usernames.remove(index);
        serverSideClientIOList.remove(index);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * Integer.hashCode(port);
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
        return this.port == comp.port;
    }

    @Override
    public String toString() {
        return "ClackClient{" +
                "port=" + port +
                ", closeConnection=" + closeConnection + '}';
    }

    public static void main(String[] args) {
        ClackServer s = null;
        if (args.length < 1) {
            s = new ClackServer();
        } else {
            s = new ClackServer(Integer.parseInt(args[0]));
        }
        System.out.println(s);
        s.start();
    }

}
