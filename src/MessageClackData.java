public class MessageClackData extends ClackData {

    //variables
    private String message;

    //constructors
    public MessageClackData(String username, String message, int type) {
        super(username, type);
        this.message = message;
    }

    public MessageClackData() {
        this("Anon", " ", 0);
    }

}