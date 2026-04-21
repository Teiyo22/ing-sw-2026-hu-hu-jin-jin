package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.server.ServerController;

import java.net.ServerSocket;

public class NetworkServer extends Thread {
    private String ip;
    private int tcpPort;
    private int rmiPort;
    private ServerSocket serverSocket;

    @Override
    public void run() {

    }
}
