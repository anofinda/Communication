package com.example.communication.controller;


import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author dongyudeng
 */
public class ReadThread extends Thread {
    Socket clientSocket;
    TextArea communicationArea;

    ReadThread(Socket socket, TextArea area) {
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