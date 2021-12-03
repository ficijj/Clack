import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
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
                tfInput.setText("");
            }
        };
        sendButton.setOnAction(sendButtonHandler);

        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
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

        primaryStage.setScene( new Scene(root, 600, 400));
        primaryStage.setTitle("Clack");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
