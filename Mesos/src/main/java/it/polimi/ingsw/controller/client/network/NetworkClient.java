package it.polimi.ingsw.controller.client.network;

import com.google.gson.*;
import it.polimi.ingsw.controller.common.messages.requests.Request;

import java.net.Socket;

public class NetworkClient implements Runnable {
    private ServerTCPInterface server;
    private Socket socket;
    private Gson gson;

    public NetworkClient(){

    }

    @Override
    public void run() {

    }

    public void sendMessage(Request request){

    }

    public void connect(String ip, int tcpPort){

    }


}
