package data;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class ClackData {

    //declaration of variables
    private String username;
    private int type;
    private Date date;

    //constructors

    /**
     * Constructor that sets username and type to user defined values and the date to the date when called
     *
     * @param username The name of the user
     * @param type     The type of data being sent
     */
    public ClackData(String username, int type) {
        this.username = username;
        this.type = type;
        date = new Date();
    }

    /**
     * Constructor that sets username to the default "Anon" and type to a user defined value
     *
     * @param type The type of data being sent
     */
    public ClackData(int type) {
        this("Anon", type);
    }

    /**
     * Constructor that sets username to the default "Anon" and type to the default 0
     */
    public ClackData() {
        this("Anon", 0);
    }

    //methods

    /**
     * Accessor for the type
     *
     * @return The type
     */
    public int getType() {
        return type;
    }

    /**
     * Accessor for the username
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gives the data contained in the class as a String
     *
     * @return The class data
     */
    public abstract String getData();

    /**
     * Gives the date the class was instantiated
     *
     * @return The date of creation
     */
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}