import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        VBox innerVBox = new VBox();
        HBox hBox = new HBox();
        VBox outerVBox = new VBox();
        TextField tfInput = new TextField("input area");
        TextField tfOutput = new TextField("output area");
        tfOutput.setEditable(false);

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

        tfOutput.setMinSize(400, 300);
        tfOutput.setMaxSize(5000,5000);

        tfInput.setMinSize(400, 30);
        tfInput.setMaxSize(5000,30);

        hBox.getChildren().add(tfInput);
        innerVBox.getChildren().add(tfOutput);
        outerVBox.getChildren().add(innerVBox);
        outerVBox.getChildren().add(hBox);
        root.getChildren().add(outerVBox);

        primaryStage.setScene( new Scene(root, 500, 500));
        primaryStage.show();
    }
}
