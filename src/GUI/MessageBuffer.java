package GUI;

import data.MessageClackData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MessageBuffer {
    private MessageClackData outGoingMessage;
    private boolean outgoingMessageToRead = false;
    private Object outgoingLock = new Object();

    private ArrayList<String> messagesList = new ArrayList<String>();
    private ObservableList<String> messageOList = FXCollections.observableList(messagesList);

    private ArrayList<String> usersList = new ArrayList<String>();
    private ObservableList<String> usersOList = FXCollections.observableList(usersList);

    private boolean closeConnection = false;

    public MessageClackData readOutgoingMessage() {
        synchronized (outgoingLock) {
            while (!outgoingMessageToRead) {
                try {
                    outgoingLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outgoingMessageToRead = false;
            outgoingLock.notifyAll();

            return outGoingMessage;
        }
    }

    public void makeOutgoingMessage(MessageClackData message) {
        synchronized (outgoingLock) {
            while (outgoingMessageToRead) {
                try {
                    outgoingLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outgoingMessageToRead = true;
            outgoingLock.notifyAll();

            this.outGoingMessage = message;
        }
    }

    public ObservableList<String> getMessageOList() {
        return messageOList;
    }

    public ObservableList<String> getUsersOList() {
        System.out.println("user list requested");
        return usersOList;
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }
}
