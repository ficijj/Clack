package main;

import data.ClackData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSideClientIO implements Runnable{
    private boolean closeConnection;

    private ClackData dataToReceiveFromClient;
    private ClackData dataToSendToClient;

    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    private ClackServer server;
    private  Socket clientSocket;

    public ServerSideClientIO(ClackServer s, Socket skt){
        closeConnection = false;

        dataToReceiveFromClient = null;
        dataToSendToClient = null;

        inFromClient = null;
        outToClient = null;

        server = s;
        clientSocket = skt;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());

            while(!closeConnection){
                receiveData();
                server.broadcast(dataToSendToClient);
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        System.out.println("Connections made...");
    }

    public void sendData() {
        try {
            outToClient.writeObject(dataToSendToClient);
            outToClient.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void setDataToSendToClient(ClackData dataToSendToClient) {
        this.dataToSendToClient = dataToSendToClient;
    }

    public void receiveData() {
        System.out.println("Receiving data...");
        try {
            dataToReceiveFromClient = (ClackData) inFromClient.readObject();
            if (dataToReceiveFromClient == null) {
                closeConnection = true;
                server.remove(this);
                System.out.println("Connection closing...");
            } else {
                System.out.println("Received data: " + dataToReceiveFromClient);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
