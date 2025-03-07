package org.example.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class DesXApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);

        Image icon = new Image("icon.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("DESX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
