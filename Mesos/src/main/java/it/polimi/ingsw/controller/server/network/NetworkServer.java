package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetworkServer extends Thread {
    private ServerSocket serverSocket;

    public NetworkServer(String ip, int tcpPort) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(ip, tcpPort));
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted() && !serverSocket.isClosed()){
            try {
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                TCPClientInterface tcpClientInterface = new TCPClientInterface(clientHandler);

                clientHandler.setClientTCPInterface(tcpClientInterface);
                ServerController.getInstance().submitListener(clientHandler);

                ServerController.getInstance().addClient(tcpClientInterface);
            } catch (SocketException ignore) {

            } catch (IOException e) {
                Logger.getInstance().print(LoggerLevel.ERROR, "TCP Server failure: " + e.getMessage());
            }
        }
    }

    public void cleanup() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                Logger.getInstance().print(LoggerLevel.SERVER, "TCP Server Socket successfully closed");
            }
        } catch (IOException ignore) {}
    }
}
