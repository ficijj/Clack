package main;

import data.ClackData;

public class ClackClient {
    private String username;
    private String hostName;
    private int port;
    private boolean closeConnection;

    private ClackData dataToSendToServer;
    private ClackData dataToReceiveFromServer;

    private static int DEFAULT_PORT = 7000;

    public ClackClient(String username, String hostName, int port) {
        this.username = username;
        this.hostName = hostName;
        this.port = port;
        closeConnection = false;
        dataToSendToServer = null;
        dataToReceiveFromServer = null;
    }

    public ClackClient(String username, String hostName) {
        this(username, hostName, DEFAULT_PORT);
    }

    public ClackClient(String username) {
        this(username, "localhost");
    }

    public ClackClient() {
        this("Anon");
    }

    public void start() {

    }

    public void readClientData() {

    }

    public void sendData() {

    }

    public void receiveData() {

    }

    public void printData() {

    }

    public String getUsername() {
        return username;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public boolean equals(ClackClient comp) {
        if (comp == null) return false;
        if (!(comp instanceof ClackClient)) return false;
        return this.toString().equals(comp.toString());
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