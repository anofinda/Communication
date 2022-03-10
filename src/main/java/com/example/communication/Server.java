package com.example.communication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author dongyudeng
 */
public class Server extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Server.class.getResource("fxmls/Server.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Server");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ioException) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application can not show.");
            alert.showAndWait();
        }
    }
}
