package GUI;

import data.ClackData;
import data.MessageClackData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import main.ClackClient;

public class GUI extends Application {
    private static MessageBuffer buffer;
    private static ClackClient client;
    public static void main(String[] args) {
        buffer = new MessageBuffer();

        createClient(args);
        Thread t = new Thread(client);
        t.start();

        launch(args);
    }

    private static void createClient(String[] args){
        int port = 0;
        String user = null;
        String host = null;
        String cmdArgs = "";
        ClackClient c = null;

        int path = 0;
        if (args.length != 0) {
            path = determineCase(args[0]);
        }

        switch (path) {
            case 0: //no args
                client = new ClackClient(buffer);
                break;
            case 1: //just username
                cmdArgs = args[0];
                client = new ClackClient(cmdArgs, buffer);
                break;
            case 2: //user and host name
                cmdArgs = args[0];
                user = cmdArgs.substring(0, cmdArgs.indexOf('@'));
//                System.out.println("username: " + user);
                host = cmdArgs.substring(cmdArgs.indexOf('@') + 1);
//                System.out.println("host name: " + host);
                client = new ClackClient(user, host, buffer);
                break;
            case 3: //username, hostname, and port number
                cmdArgs = args[0];
                user = cmdArgs.split("@")[0];
                host = (cmdArgs.split("@")[1]).split(":")[0];
                port = Integer.parseInt((cmdArgs.split("@")[1]).split(":")[1]);
                c = new ClackClient(user, host, port, buffer);
                break;
        }
    }

    private static int determineCase(String args) {
        if (!args.contains("@") && !args.contains(":")) {
            return 1;
        } else if (args.contains("@") && !args.contains(":")) {
            return 2;
        } else if (args.contains("@") && args.contains(":")) {
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20,20,20,20));
        VBox outerVBox = new VBox();

        HBox hBoxOutput = new HBox();
        TextField tfOutput = new TextField("output area");
        TextField userList = new TextField("users");

        HBox hBoxInput = new HBox();
        TextField tfInput = new TextField("input area");
        Button sendButton = new Button("Send");
        Button mmButton = new Button("MM");

        tfOutput.setEditable(false);

        userList.setEditable(false);

        EventHandler<ActionEvent> sendButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buffer.makeMessage(new MessageClackData(client.getUsername(), tfInput.getText(), ClackData.CONST_SEND_MESSAGE));
                tfInput.setText("");
            }
        };
        sendButton.setOnAction(sendButtonHandler);

        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    buffer.makeMessage(new MessageClackData(client.getUsername(), tfInput.getText(), ClackData.CONST_SEND_MESSAGE));
                    tfInput.setText("");
                }
            }
        };
        tfInput.setOnKeyPressed(keyHandler);

        root.setAlignment(Pos.BOTTOM_CENTER);
        tfOutput.setAlignment(Pos.BOTTOM_LEFT);
        userList.setAlignment(Pos.TOP_LEFT);

        tfOutput.setMinSize(400, 300);
        tfOutput.setMaxSize(5000,5000);

        userList.setMinSize(90, 300);
        userList.setMaxSize(90, 300);

        tfInput.setMinSize(400, 30);
        tfInput.setMaxSize(5000,30);

        sendButton.setMinSize(45,30);
        sendButton.setMaxSize(45,30);
        mmButton.setMinSize(45,30);
        mmButton.setMaxSize(45,30);

        root.getChildren().add(outerVBox);

        outerVBox.getChildren().add(hBoxOutput);
        outerVBox.getChildren().add(hBoxInput);

        hBoxOutput.getChildren().add(tfOutput);
        hBoxOutput.getChildren().add(userList);

        hBoxInput.getChildren().add(tfInput);
        hBoxInput.getChildren().add(sendButton);
        hBoxInput.getChildren().add(mmButton);

        root.getStylesheets().add(getClass().getResource("GUI/application.css").toExternalForm());

        primaryStage.setScene( new Scene(root, 600, 400));
        primaryStage.setTitle("Clack");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
