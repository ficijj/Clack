package main;

import data.ClackData;

public class ClackServer {

    /**
     * Declaration of all variables for class
     */

    private int port;

    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;

    private static final int DEFAULT_PORT = 7000;

    /**
     * Boolean checks if connection is closed or not
     */
    private boolean closeConnection;


    /**
     * Constructors
     *
     * @param port - port connection number constructor
     */
    public ClackServer(int port) {
        this.port = port;
        dataToReceiveFromClient = null;
        dataToSendToClient = null;
    }

    /**
     * default constructor for port and data sent and received between client and server
     */
    public ClackServer() {
        this(7000);
    }

    //methods

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
        return 0;
    }


    public boolean equals(ClackServer comp) {
        if (comp == null) return false;
        if (!(comp instanceof ClackServer)) return false;
        return this.toString().equals(comp.toString());
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
