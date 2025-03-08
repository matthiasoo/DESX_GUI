package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class DesXApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DesXApplication.fxml"));
            Scene scene = new Scene(root);

            Image icon = new Image("icon.png");

            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("DESX GUI");
            primaryStage.setWidth(500);
            primaryStage.setHeight(700);
            primaryStage.setResizable(false);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
