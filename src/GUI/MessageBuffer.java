package GUI;

import data.MessageClackData;

public class MessageBuffer {
    private MessageClackData message;
    private boolean messageRead = false;
    private Object lock = new Object();

    public synchronized MessageClackData readMessage(){
        while(messageRead){
            try{
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messageRead = false;
        lock.notify();

        return message;
    }

    public synchronized void makeMessage(MessageClackData message){
        while(!messageRead){
            try{
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messageRead = true;
        lock.notify();

        this.message = message;
    }
}
