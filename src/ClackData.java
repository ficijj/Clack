import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class ClackData {

    //declaration of variables
    private String username;
    private int type;
    private Date date;

    //constructors
    public ClackData(String username, int type) {
        this.username = username;
        this.type = type;
        date = new Date();
    }

    public ClackData(int type) {
        this("Anon", type);
    }

    public ClackData() {
        this("Anon", 0);
    }

    //methods
    public int getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public abstract String getData();

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}