package data;

public class MessageClackData extends ClackData {

    //variables
    private String message;

    //constructors

    /**
     * Constructor that sets all variables to user defined values
     *
     * @param username The name of the user
     * @param message  The contents of the message being sent
     * @param type     The type of data being sent
     */
    public MessageClackData(String username, String message, int type) {
        super(username, type);
        this.message = message;
    }

    /**
     * Constructor that sets all variables to default values
     */
    public MessageClackData() {
        this("Anon", " ", 0);
    }

    @Override
    public String getData() {
        return message;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Tells you if the username and message contents of the 2 classes is the same
     *
     * @param comp The class to compare
     * @return True if they are exactly the same and false otherwise
     */
    public boolean equals(MessageClackData comp) {
        return this.message.equals(comp.message) && this.getUsername().equals(comp.getUsername());
    }

    @Override
    public String toString() {
        return "user: " + getUsername() + ", date: " + getDate() + ", type: " + getType() + ", message: " + this.getData();
    }

}