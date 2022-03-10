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
public class Client extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Client.class.getResource("fxmls/Client.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Client");
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
