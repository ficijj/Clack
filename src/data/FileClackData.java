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


     // Constructor that sets all variables to default values

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

    public String getData(String key) {return decrypt(fileContents, key);}

    /**
     * Read the contents of the file
     */
    public void readFileContents() {
        try {
            fileContents = "";
            FileReader reader = new FileReader(fileName);
            boolean doneReadingFile = false;

            while (!doneReadingFile) {
                int nextCharacterAsInteger = reader.read();
                doneReadingFile = nextCharacterAsInteger == -1;

                if (!doneReadingFile) {
                    char nextCharacter = (char)nextCharacterAsInteger;
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

    public void readFileContents(String key) {
        try {
            fileContents = "";
            FileReader reader = new FileReader(fileName);
            boolean doneReadingFile = false;

            while (!doneReadingFile) {
                int nextCharacterAsInteger = reader.read();
                doneReadingFile = nextCharacterAsInteger == -1;

                if (!doneReadingFile) {
                    char nextCharacter = (char)nextCharacterAsInteger;
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
     * Write to the contents of the file
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
