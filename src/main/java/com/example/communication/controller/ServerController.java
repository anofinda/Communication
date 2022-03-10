package com.example.communication.controller;

import com.example.communication.Server;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.event.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

/**
 * @author dongyudeng
 */
public class ServerController {
    static BufferedWriter writer;
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
    public void startClicked(Event event) {
        String portString = portField.getText();
        try {
            int port=Integer.parseInt(portString);
            ServerWriteHandler handler=new ServerWriteHandler(port,communicationArea);
            communicationArea.appendText("Server starting…" + "\n");
            handler.start();
        } catch (NumberFormatException numberFormatException) {
            communicationArea.appendText("Port Illegal!" + "\n");
        }
    }
}

class ServerWriteHandler extends Thread {
    int port;
    TextArea communicationArea;
    ServerWriteHandler(int p,TextArea area){
        port=p;
        communicationArea=area;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            Socket clientSocket=serverSocket.accept();
            communicationArea.appendText("Client Connected..." + "\n");
            ServerController.writer=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8));
            ServerReadHandler handler=new ServerReadHandler(clientSocket,communicationArea);
            handler.start();
        } catch (IOException e) {
            communicationArea.appendText("Can not be connected!" + "\n");
        }
    }
}

class ServerReadHandler extends Thread {
    Socket clientSocket;
    TextArea communicationArea;

    ServerReadHandler(Socket socket, TextArea area) {
        clientSocket = socket;
        communicationArea = area;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            while (true) {
                try {
                    communicationArea.appendText("client:" + reader.readLine() + "\n");
                } catch (IOException e) {
                    communicationArea.appendText("read failed" + "\n");
                }
            }
        } catch (IOException e) {
            communicationArea.appendText("Read Failed!");
        }
    }
}