package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.utils.controller.RequestDeserializer;
import it.polimi.ingsw.utils.controller.ResponseSerializer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {
    private TCPClientInterface tcpClientInterface;
    private Socket socket;
    private Gson gson;
    private BufferedReader input;
    private BufferedWriter output;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

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
                tcpClientInterface.handleMessage(request);
            }
        } catch (IOException e) {
            System.out.println("Error while reading message in TCP: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    public void sendMessage(Response response) {
        try {
            String message = gson.toJson(response);
            output.write(message);
            output.newLine();
            output.flush();
        } catch (IOException e) {
            cleanup();
        }
    }

    public void setClientTCPInterface(TCPClientInterface tcpClientInterface){
        this.tcpClientInterface = tcpClientInterface;
    }

    public void cleanup() {
        try {
            new Thread(() -> { ServerController.getInstance().disconnectClient(tcpClientInterface); }).start();
            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException ignore) { }
    }
}