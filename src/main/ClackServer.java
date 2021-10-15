package main;

import data.ClackData;

public class ClackServer {
    private int port;

    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;

    private static final int DEFAULT_PORT = 7000;

    private boolean closeConnection;

    /**
     * Constructor that sets the port number to the provided value and all other instance variables to their defaults
     *
     * @param port - port connection number
     */
    public ClackServer(int port) {
        this.port = port;
        dataToReceiveFromClient = null;
        dataToSendToClient = null;
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
    }

    public void receiveData() {
    }

    public void sendData() {
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

}
