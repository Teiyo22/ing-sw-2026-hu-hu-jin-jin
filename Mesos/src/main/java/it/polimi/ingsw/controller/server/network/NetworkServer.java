package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.server.ServerController;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
                TCPClientInterface tcpClientInterface = new TCPClientInterface(clientHandler);

                clientHandler.setClientTCPInterface(tcpClientInterface);
                ServerController.getInstance().submitListener(clientHandler);

                ServerController.getInstance().addClient(tcpClientInterface);
            } catch (IOException e) {
                ServerController.getInstance().stopServer();
            }
        }
    }

    public void cleanup() {
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
        } catch (IOException ignore) {}
    }
}
