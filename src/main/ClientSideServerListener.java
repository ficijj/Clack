package main;

import GUI.MessageBuffer;

public class ClientSideServerListener implements Runnable{
    private ClackClient client;
    private MessageBuffer buffer;
    public ClientSideServerListener(ClackClient c, MessageBuffer b){
        client = c;
        buffer = b;
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
        while(!buffer.isCloseConnection()){
            client.receiveData();
            if(!client.isCloseConnection()) {
                client.printData();
            }
        }
    }
}
