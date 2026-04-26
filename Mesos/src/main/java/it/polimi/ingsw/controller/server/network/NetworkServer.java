package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class NetworkServer extends Thread {
    private String ip;
    private int tcpPort;
    private ServerSocket serverSocket;
    private ServerController serverController;

    public NetworkServer(ServerController serverController, String ip, int tcpPort) throws IOException {
        this.ip = ip;
        this.tcpPort = tcpPort;
        this.serverController = serverController;
        serverSocket = new ServerSocket(tcpPort);

    }

    @Override
    public void run() {
        Gson gson = new Gson();
        Socket clientSocket = null;
        while(true){
            try {
                if ((clientSocket = this.serverSocket.accept()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ClientHandler clientHandler = new ClientHandler(clientSocket,gson);
            ClientTCPInterface clientTCPInterface = new ClientTCPInterface(clientHandler, serverController);
            clientHandler.setClientTCPInterface(clientTCPInterface);
            clientHandler.start();

        }

    }
}
