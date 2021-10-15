package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
     * Default constructor which sets all values to their respective defaults
     */
    public FileClackData() { this("Anon", "", 0); }

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

    @Override
    public String getData(String key) {
        return decrypt(fileContents, key);
    }

    /**
     * Reads from specified file (fileName) into fileContents
     *
     * @throws IOException If the file cannot be found or there is an error reading/closing the file
     */
    public void readFileContents() throws IOException {
        try {
            fileContents = "";
            FileReader reader = new FileReader(fileName);
            boolean doneReadingFile = false;

            while (!doneReadingFile) {
                int nextCharacterAsInteger = reader.read();
                doneReadingFile = nextCharacterAsInteger == -1;

                if (!doneReadingFile) {
                    char nextCharacter = (char) nextCharacterAsInteger;
                    fileContents += nextCharacter;
                }
            }
            reader.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("File cannot be found, no file read.");
        } catch (IOException ioe) {
            System.err.println("Error reading or closing file.");
        }
    }

    /**
     * Reads from specified file (fileName) into fileContents while encrypting
     *
     * @param key To use in the encryption process
     * @throws IOException If the file cannot be found or there is an error reading/closing the file
     */
    public void readFileContents(String key) throws IOException {
        try {
            fileContents = "";
            FileReader reader = new FileReader(fileName);
            boolean doneReadingFile = false;

            while (!doneReadingFile) {
                int nextCharacterAsInteger = reader.read();
                doneReadingFile = nextCharacterAsInteger == -1;

                if (!doneReadingFile) {
                    char nextCharacter = (char) nextCharacterAsInteger;
                    fileContents += nextCharacter;
                }
            }
            fileContents = encrypt(fileContents, key);
            reader.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("This file cannot be found, no file read.");
        } catch (IOException ioe) {
            System.err.println("Error reading or closing file.");
        }
    }

    /**
     * Writes from fileContents to specified file (fileName) while decrypting
     *
     * @param key To use in the decryption process
     */
    public void writeFileContents(String key) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(decrypt(fileContents, key));
            writer.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("This file cannot be found, no file read.");
        } catch (IOException ioe) {
            System.err.println("Error writing or closing file.");
        }
    }

    /**
     * Writes from fileContents to specified file (fileName)
     */
    public void writeFileContents() {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(fileContents);
            writer.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println("This file cannot be found, no file read.");
        } catch (IOException ioe) {
            System.err.println("Error writing or closing file.");
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + fileName.hashCode();
        result = 37 * result + fileContents.hashCode();
        return result;
    }

    /**
     * Tells you if the name of the file and the contents are the same
     *
     * @param comp The class to compare
     * @return True if the file name and contents are exactly the same and false otherwise
     */
    public boolean equals(FileClackData comp) {
        if (comp == null) return false;
        if (!(comp instanceof FileClackData)) return false;
        return this.fileName.equals(comp.fileName) && this.fileContents.equals(comp.fileContents);
    }

    @Override
    public String toString() {
        return "FileClackData{" +
                "username=" + this.getUsername() +
                ", fileName=" + this.fileName +
                ", fileContents=" + this.getData() +
                ", type=" + this.getType() +
                ", date= " + this.getDate() +
                '}';
    }
}
