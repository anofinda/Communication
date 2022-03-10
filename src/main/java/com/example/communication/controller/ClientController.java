package com.example.communication.controller;

import javafx.event.Event;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author dongyudeng
 */
public class ClientController{
    private BufferedWriter writer;
    @FXML
    private TextArea communicationArea;
    @FXML
    private TextField portField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField sayField;
    @FXML
    private Label ipLabel;
    @FXML
    private Button sayButton;
    @FXML
    private Label portLabel;
    @FXML
    private Button connectButton;
    @FXML
    public void connectClicked(Event event) {
        String ipString =ipField.getText(),portString=portField.getText();
        try{
            int port=Integer.parseInt(portString);
            ClientConnectThread connectThread=new ClientConnectThread(ipString,port,this);
        }
        catch (NumberFormatException numberFormatException){
            communicationArea.appendText("Port Illegal!"+"\n");
        }
    }
    @FXML
    public void sayClicked(Event event) throws IOException {
        String sayText=sayField.getText();
        communicationArea.appendText("client:" + sayText+"\n");
        writer.write(sayText);
        writer.newLine();
        writer.flush();
    }
    void awake(Socket clientSocket) throws IOException {
        communicationArea.appendText("Connect to server…"+"\n");
        writer=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
        ReadThread readThread=new ReadThread(clientSocket,communicationArea);
        readThread.start();
    }
}
class ClientConnectThread extends Thread{
    String ip;
    int port;
    ClientController controller;
    ClientConnectThread(String ipStirng,int p,ClientController clientController){
        ip=ipStirng;
        port=p;
        controller=clientController;
    }
    @Override
    public void run() {
        try {
            Socket clientSocket=new Socket(ip,port);
            controller.awake(clientSocket);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Server can not be connected.");
            alert.showAndWait();
        }

    }
}