package test;

import main.ClackServer;

public class TestClackServer {
    public static void main(String [] args) {
        ClackServer testServer1 = new ClackServer(2200);
        System.out.println(testServer1);

        ClackServer testServer2 = new ClackServer();
        System.out.println(testServer2);

        ClackServer testServer3 = new ClackServer(-1);
        System.out.println("negative port test: " + testServer3);

        System.out.println(testServer2.getPort());

        System.out.println(testServer1 + " is the same as " + testServer1 + ": " + testServer1.equals(testServer1));
        System.out.println(testServer1 + " is the same as " + testServer2 + ": " + testServer1.equals(testServer2));
    }
}
