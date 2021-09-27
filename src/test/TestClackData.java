package test;

import data.ClackData;
import data.MessageClackData;
import data.FileClackData;

public class TestClackData {
  ClackData testData = new MessageClackData("Test Username","message test", 44);
  ClackData testDataFile = new FileClackData( "Test Username","file nameTest", 44);
}
