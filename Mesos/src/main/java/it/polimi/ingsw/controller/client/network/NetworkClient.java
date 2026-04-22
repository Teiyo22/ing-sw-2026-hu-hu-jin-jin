package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;
import java.io.*;
import java.net.UnknownHostException;
import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.controller.common.messages.responses.Response;

import java.net.Socket;

public class NetworkClient extends Thread {
    private ServerTCPInterface server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private final Gson gson;

    public NetworkClient(){
        this.server = null;
        this.socket = null;
        this.input = null;
        this.output = null;
        this.gson = new Gson();
    }


    /** The NetworkClient runs and listens to messages from the server.
     * When a message is received it gets passed to the ServerTCPInterface to handle it.
     * */
    @Override
    public void run() {
        String line;
        try {
            while ((line = input.readLine()) != null) {
                Response response = gson.fromJson(line, Response.class);
                server.handleMessage(response);
            }
        } catch (IOException e) {
            System.out.println("Error while reading message in TCP: " + e.getMessage());
        }
    }


    /** Method to serialize and send messages to the server.
     * @param request The message to serialize and send.
     * */
    public void sendMessage(Request request){
        String msg = gson.toJson(request);
        output.println(msg);
    }

    public void connect(String ip, int tcpPort) throws UnknownHostException, IOException {
        this.socket = new Socket(ip, tcpPort);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream(), true);
        new Thread(this).start();
    }

    public void setServer(ServerTCPInterface server) {
        this.server = server;
    }
}
