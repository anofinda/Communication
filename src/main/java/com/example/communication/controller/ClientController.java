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
    static BufferedReader reader;
    static BufferedWriter writer;
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
            Socket clientSocket=new Socket(ipString,Integer.parseInt(portString));
            communicationArea.appendText("Connect to server…"+"\n");
            reader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),StandardCharsets.UTF_8));
            writer=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            ReadHandler handler= new ReadHandler();
            handler.start();
        }
        catch (NumberFormatException numberFormatException){
            communicationArea.appendText("Port Illegal!"+"\n");
        }
        catch (IOException ioException){
            communicationArea.appendText("Connect failed!"+"\n");
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

    class ReadHandler extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    communicationArea.appendText("server:"+reader.readLine()+"\n");
                } catch (IOException e) {
                    communicationArea.appendText("read failed"+"\n");
                }
            }
        }
    }
}