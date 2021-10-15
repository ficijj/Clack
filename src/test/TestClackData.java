package test;

import data.*;
import main.*;


public class TestClackData {
  public static void main(String [] args) {
    ClackData testData1 = new MessageClackData("Test Username", "message test", 2);
    System.out.println(testData1);

    ClackData testData2 = new MessageClackData();
    System.out.println(testData2);

//  System.out.println(testData2.getData());

//  System.out.println(testData1 + " is the same as " + testData1 + ": " + testData1.equals(testData1));
//  System.out.println(testData1 + " is the same as " + testData2 + ": " + testData1.equals(testData2));

//  System.out.println();

//  ClackData testDataFile1 = new FileClackData("Test Username", "Test File Name", 3);
//  System.out.println(testDataFile1);

//  ClackData testDataFile2 = new FileClackData();
//  System.out.println(testDataFile2);

//  ((FileClackData) testDataFile2).setFileName("new file name");
//  System.out.println(((FileClackData) testDataFile2).getFileName());

//  System.out.println(testDataFile2.getData());

//  System.out.println(testDataFile1 + " is the same as " + testDataFile1 + ": " + testDataFile1.equals(testDataFile1));
//  System.out.println(testDataFile1 + " is the same as " + testDataFile2 + ": " + testDataFile1.equals(testDataFile2));


  String key = "time";
//  String encrypted = encrypt("hello world!", key);
//  String decrypted = testData1.decrypt(encrypted, key);
//  System.out.println(encrypted);
//  System.out.println(decrypted);
    ClackData testing = new MessageClackData("username", "hello test",key, 2);
    String decrypted = testing.getData(key);
    System.out.println(decrypted);

  }
}
