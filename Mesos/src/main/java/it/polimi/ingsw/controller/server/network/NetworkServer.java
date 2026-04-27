package it.polimi.ingsw.controller.server.network;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.ServerController;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class NetworkServer extends Thread {
    private ServerSocket serverSocket;

    public NetworkServer(String ip, int tcpPort) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(ip, tcpPort));
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                ClientTCPInterface clientTCPInterface = new ClientTCPInterface(clientHandler);

                clientHandler.setClientTCPInterface(clientTCPInterface);
                clientHandler.start();

                ServerController.getInstance().addClient(clientTCPInterface);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
