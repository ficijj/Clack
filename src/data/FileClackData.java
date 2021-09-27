package data;

public class FileClackData extends ClackData {
    private String fileName;
    private String fileContents;

    /**
     * Constructor that sets all variables to user defined values
     *
     * @param username The name of the user
     * @param fileName The name of the file being sent
     * @param type     The type of data being sent
     */
    public FileClackData(String username, String fileName, int type) {
        super(username, type);
        this.fileName = fileName;
        fileContents = null;
    }

    /**
     * Constructor that sets all variables to default values
     */
    public FileClackData() {
        this("Anon", "", 0);
    }

    /**
     * Mutator for the file name
     *
     * @param fileName What the file name is being set to
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Accessor for the file name
     *
     * @return The name of the file
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getData() {
        return fileContents;
    }

    /**
     * Read the contents of the file
     */
    public void readFileContents() {

    }

    /**
     * Write to the contents of the file
     */
    public void writeFileContents() {

    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Tells you if the name of the file and the contents are the same
     *
     * @param comp The class to compare
     * @return True if the file name and contents are exactly the same and false otherwise
     */
    public boolean equals(FileClackData comp) {
        if(comp == null) return false;
        if(!(comp instanceof FileClackData)) return false;
        return this.toString().equals(comp.toString());
    }

    @Override
    public String toString() {
        return "FileClackData{" +
                "username=" + this.getUsername() +
                ", fileName=" + this.fileName +
                ", fileContents=" + this.getData() +
                ", type=" + this.getType() +
                '}';
    }
}
