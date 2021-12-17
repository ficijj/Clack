package data;

import javafx.scene.image.Image;

import java.io.InputStream;

public class PictureClackData extends ClackData{
    private String username;
    private int type;
    private Image image;

    public PictureClackData(String username, InputStream is, int type){
        super(username, type);
        image = new Image(is);
    }

    public PictureClackData(){
        this("Anon", null, CONST_SEND_PICTURE);
    }

    /**
     * Gives the data contained in the class as a String
     *
     * @return The class data
     */
    @Override
    public Image getData() {
        return image;
    }

    /**
     * Gives the data contained in the class as a String after decrypting it
     *
     * @param key The decryption key
     * @return The decrypted data
     */
    @Override
    public Image getData(String key) {
        return image;
    }

}
