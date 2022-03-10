package com.example.communication.controller;

import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.event.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author dongyudeng
 */
public class ServerController {
    private BufferedWriter writer;
    @FXML
    private TextArea communicationArea;
    @FXML
    private TextField sayField;
    @FXML
    private TextField portField;
    @FXML
    private Button startButton;
    @FXML
    private Button sayButton;
    @FXML
    private Label portLabel;

    @FXML
    public void sayClicked(Event event) throws IOException {
        String sayText = sayField.getText();
        communicationArea.appendText("server:" + sayText);
        writer.write(sayText);
        writer.newLine();
        writer.flush();
    }

    @FXML
    public void startClicked(Event event) throws IOException, InterruptedException {
        String portString = portField.getText();
        try {
            int port=Integer.parseInt(portString);
            SocketAcceptThread acceptThread=new SocketAcceptThread(port,this);
            acceptThread.start();
            communicationArea.appendText("Server starting…" + "\n");
            sayButton.setDisable(true);
        } catch (NumberFormatException numberFormatException) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Port Illegal.");
            alert.showAndWait();
        }
    }
    void awake(Socket clientSocket) throws IOException {
        sayButton.setDisable(false);
        writer=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8));
        ReadThread readThread=new ReadThread(clientSocket,communicationArea);
        readThread.start();
    }
}

class SocketAcceptThread extends Thread{
    ServerController controller;
    int port;
    SocketAcceptThread(int p,ServerController serverController){
        port=p;
        controller=serverController;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket=serverSocket.accept();
            controller.awake(clientSocket);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Port can not be connected.");
            alert.showAndWait();
        }
    }
}

