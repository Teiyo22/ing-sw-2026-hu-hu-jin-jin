package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;
import java.io.*;
import java.net.UnknownHostException;
import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.controller.common.messages.responses.Response;
import it.polimi.ingsw.utils.controller.RequestSerializer;
import it.polimi.ingsw.utils.controller.ResponseDeserializer;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Request.class, new RequestSerializer())
                .registerTypeAdapter(Response.class, new ResponseDeserializer())
                .create();
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
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        new Thread(this).start();
    }

    public void setServer(ServerTCPInterface server) {
        this.server = server;
    }
}
