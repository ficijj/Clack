package data;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class ClackData implements Serializable {
    public static final int CONST_LIST_USERS = 0;
    public static final int CONST_SEND_MESSAGE = 1;
    public static final int CONST_SEND_FILE = 2;
    public static final int CONST_SEND_PICTURE = 3;

    private String username;
    private int type;
    private Date date;

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
    public abstract Object getData();

    /**
     * Gives the data contained in the class as a String after decrypting it
     *
     * @param key The decryption key
     * @return The decrypted data
     */
    public abstract Object getData(String key);

    /**
     * Gives the date the class was instantiated
     *
     * @return The date of creation
     */
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * Data encryption algorithm that uses a vigenere cipher, ignoring all special characters
     *
     * @param inputStringToEncrypt The String you want to encrypt
     * @param key                  The key to use for encryption
     * @return The fully encrypted data
     */
    protected String encrypt(String inputStringToEncrypt, String key) {
        String output = "";
        key = key.toLowerCase();
        // single case dictionary
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String low = inputStringToEncrypt.toLowerCase(); // to work on

        for (int i = 0, posInKey = 0; i < inputStringToEncrypt.length(); i++) {
            int locOfKeyInAlpha = alphabet.indexOf(key.charAt(posInKey % key.length()));
            int locOfInputInAlpha = alphabet.indexOf(low.charAt(i));
            String out = ""; // the character we'll add
            if (!(low.charAt(i) >= 'a' && low.charAt(i) <= 'z')) {
                out += low.charAt(i); // keep special chars untouched
            } else if (locOfInputInAlpha > -1) { // only if valid
                out = String.valueOf((alphabet.charAt((locOfKeyInAlpha + locOfInputInAlpha) % alphabet.length()))); // get the ciphered value
                posInKey++;
            }
            if (low.charAt(i) != inputStringToEncrypt.charAt(i)) { // if input and lower case are different
                out = out.toUpperCase();
            }
            output += out;
        }
        return output;
    }

    /**
     * Data decryption algorithm that uses a vigenere cipher, ignoring all special characters
     *
     * @param inputStringToDecrypt The String you want to decrypt
     * @param key                  The key to use for decryption
     * @return The decrypted String
     */
    protected String decrypt(String inputStringToDecrypt, String key) {
        String output = "";
        // single case dictionary
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String low = inputStringToDecrypt.toLowerCase(); // to work on

        for (int i = 0, posInKey = 0; i < inputStringToDecrypt.length(); i++) {
            int locOfKeyInAlpha = alphabet.indexOf(key.charAt(posInKey % key.length()));
            int locOfInputInAlpha = alphabet.indexOf(low.charAt(i));
            String out = ""; // the character we'll add
            if (!(low.charAt(i) >= 'a' && low.charAt(i) <= 'z')) {
                out += low.charAt(i); // keep spaces untouched
            } else if (locOfInputInAlpha > -1) { // only if valid
                int newValPos = (locOfInputInAlpha - locOfKeyInAlpha + alphabet.length()) % alphabet.length();
                if (newValPos < 0) {
                    newValPos += 26;
                }
                out = String.valueOf((alphabet.charAt(newValPos))); // get the ciphered value
                posInKey++;
            }
            if (low.charAt(i) != inputStringToDecrypt.charAt(i)) { // if input and lower case are different
                out = out.toUpperCase();
            }
            output += out;
        }
        return output;
    }

}