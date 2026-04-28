package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.controller.common.messages.responses.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ClientTCPInterface clientInterface;
    private Socket socket;
    private Gson gson;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket clientSocket){
        this.socket = clientSocket;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Request.class, new RequestDeserializer())
                .registerTypeAdapter(Response.class, new ResponseSerializer())
                .create();
    }

    @Override
    public void run() {
        String line;

        try {
            while ((line = input.readLine()) != null) {
                Request request = gson.fromJson(line, Request.class);
                clientInterface.handleMessage(request);
            }
        } catch (IOException e) {
            System.out.println("Error while reading message in TCP: " + e.getMessage());
        }
    }

    public void sendMessage(Response response) {
        String message = gson.toJson(response);
        output.println(message);
    }

    public void setClientTCPInterface(ClientTCPInterface clientInterface){
        this.clientInterface = clientInterface;
    }
}