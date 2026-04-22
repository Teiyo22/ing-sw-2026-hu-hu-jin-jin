package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;
import it.polimi.ingsw.controller.server.network.ClientHandler;
import it.polimi.ingsw.controller.common.messages.requests.Request;

import java.net.Socket;

public class NetworkClient {
    private ClientHandler clientHandler;
    private Socket socket;
    private JsonObject jsonObject;

    public NetworkClient(String ip, int port){

    }

    public void run(){

    }

    public void sendMessage(Request request){

    }

    public void connect(String ip, int tcpPort){

    }


}
