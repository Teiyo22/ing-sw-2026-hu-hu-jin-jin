package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.common.messages.responses.Response;

import java.net.Socket;

public class ClientHandler extends Thread {
    private ClientTCPInterface clientInterface;
    private Socket socket;
    private Gson gson;

    ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
    }

    public void sendMessage(Response response) {
    }


}