package GUI;

import data.ClackData;
import data.MessageClackData;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private static ClackClient client = null;

    public static void main(String[] args) {
        buffer = new MessageBuffer();

        launch();
    }

    private static void createClient(String username, String hostname, String port) {
        if (username == null && hostname == null && new Integer(port) == null) {
            client = new ClackClient(buffer);
        } else if (username != null && hostname == null && new Integer(port) == null) {
            client = new ClackClient(username, buffer);
        } else if (username != null && hostname != null && new Integer(port) == null) {
            client = new ClackClient(username, hostname, buffer);
        } else {
            client = new ClackClient(username, hostname, Integer.parseInt(port), buffer);
            System.out.println(client);
        }
    }

    @Override
    public void stop(){
        buffer.setCloseConnection(true);
        buffer.makeOutgoingMessage(null);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20, 20, 20, 20));
        VBox outerVBox = new VBox();

        HBox hBoxOutput = new HBox();
        TextArea tfOutput = new TextArea();
        TextArea userList = new TextArea();

        HBox hBoxInput = new HBox();
        TextField tfInput = new TextField();
        Button sendButton = new Button("Send");
        Button mmButton = new Button("MM");

        tfOutput.setWrapText(true);
        tfOutput.setEditable(false);
        userList.setWrapText(true);
        userList.setEditable(false);

        tfOutput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tfOutput.setScrollTop(Double.MAX_VALUE);
            }
        });

        buffer.getUsersOList().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                userList.setText(c.getList().toString());
            }
        });

        buffer.getMessageOList().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                tfOutput.appendText(c.getList().get(c.getList().size() - 1));
            }
        });

        EventHandler<ActionEvent> sendButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buffer.makeOutgoingMessage(new MessageClackData(client.getUsername(), tfInput.getText(), ClackData.CONST_SEND_MESSAGE));
            }
        };
        sendButton.setOnAction(sendButtonHandler);

        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    buffer.makeOutgoingMessage(new MessageClackData(client.getUsername(), tfInput.getText(), ClackData.CONST_SEND_MESSAGE));
                    tfInput.setText("");
                }
            }
        };
        tfInput.setOnKeyPressed(keyHandler);

        root.setAlignment(Pos.BOTTOM_CENTER);
//        tfOutput.setAlignment(Pos.BOTTOM_LEFT);
//        userList.setAlignment(Pos.TOP_LEFT);

        tfOutput.setMinSize(400, 300);
        tfOutput.setMaxSize(5000, 5000);

        userList.setMinSize(90, 300);
        userList.setMaxSize(90, 300);

        tfInput.setMinSize(478, 30);
        tfInput.setMaxSize(5000, 30);

        sendButton.setMinSize(45, 30);
        sendButton.setMaxSize(45, 30);
        mmButton.setMinSize(45, 30);
        mmButton.setMaxSize(45, 30);

        root.getChildren().add(outerVBox);

        outerVBox.getChildren().add(hBoxOutput);
        outerVBox.getChildren().add(hBoxInput);

        hBoxOutput.getChildren().add(tfOutput);
        hBoxOutput.getChildren().add(userList);

        hBoxInput.getChildren().add(tfInput);
        hBoxInput.getChildren().add(sendButton);
        hBoxInput.getChildren().add(mmButton);

        root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        FlowPane signIn = new FlowPane();
        signIn.setPadding(new Insets(20, 20, 20, 20));

        VBox sIVbox = new VBox();

        Label username = new Label("username: ");
        TextField tfUsername = new TextField("anon");

        Label hostName = new Label("host: ");
        TextField tfHostName = new TextField("localhost");

        Label port = new Label("port: ");
        TextField tfPort = new TextField("7000");

        Button signInButton = new Button("sign in");
        signInButton.setAlignment(Pos.CENTER);

        EventHandler<ActionEvent> signInButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createClient(tfUsername.getText(), tfHostName.getText(), tfPort.getText());
                Thread c = new Thread(client);
                c.start();
                primaryStage.setScene(new Scene(root, 600, 400));
            }
        };
        signInButton.setOnAction(signInButtonHandler);

        sIVbox.getChildren().addAll(username, tfUsername, hostName, tfHostName, port, tfPort, signInButton);

        sIVbox.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(new Scene(sIVbox, 600, 400));
        primaryStage.setTitle("Clack");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
