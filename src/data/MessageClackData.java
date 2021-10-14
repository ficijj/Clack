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

    /**
     *
     * @param userName username of the user
     * @param message message to be encrypted or decrypted
     * @param key used to decrypt and encrypt messages
     * @param type port
     */


    public MessageClackData(String userName, String message, String key, int type){

        this(userName, message, type);
        encrypt(message, key);

    }


    @Override
    public String getData() {
        return message;
    }

    public String getData(String key) {return decrypt(message, key);} /**  ||3|| **/

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
        if(comp == null) return false;
        if(!(comp instanceof MessageClackData)) return false;
        return this.toString().equals(comp.toString());
    }

    @Override
    public String toString() {
        return "MessageClackData{" +
                "username=" + this.getUsername() +
                ", message=" + this.getData() +
                ", type=" + this.getType() +
                '}';
    }

}