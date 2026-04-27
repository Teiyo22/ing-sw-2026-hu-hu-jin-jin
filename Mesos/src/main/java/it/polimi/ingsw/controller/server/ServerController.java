package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.common.messages.responses.Response;
import it.polimi.ingsw.controller.server.network.ClientTCPInterface;
import it.polimi.ingsw.controller.server.network.NetworkServer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;

import java.util.HashMap;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerController extends VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private Map<Integer, LobbyController> waitingLobbies;
    private Map<Integer, LobbyController> runningLobbies;
    private Map<Integer, VirtualClient> clients;
    private int nextClientID = 1;
    private int nextLobbyID = 1;

    private ServerController(){
        this.clients = new HashMap<>();
        this.waitingLobbies = new HashMap<>();
        this.runningLobbies = new HashMap<>();

    }

    public static synchronized ServerController getInstance() {
        if(instance == null){
            instance = new ServerController();
        }
        return instance;
    }

    public void broadcast(Response response){

    }

    @Override
    public synchronized void addClient(VirtualClient client) {
        int clientID = nextClientID++;
        client.setID(clientID);
        clients.put(clientID, client);
    }

    @Override
    public synchronized void createLobby(int clientID, int playerNum, String playerName, Totem totem) {
        VirtualClient client = clients.get(clientID);
        Map<Integer, String> players = new HashMap<>();
        int lobbyID= nextLobbyID++;
        LobbyController lobbyController = new LobbyController(client, lobbyID, playerNum, playerName, totem);

        players.put(clientID, playerName);
        waitingLobbies.put(lobbyID, lobbyController);
        client.setLobby(clientID, lobbyID, playerNum, players, playerName, totem);
    }

    @Override
    public synchronized void joinLobby(int clientID, int lobbyID, String playerName, Totem totem) {
        VirtualClient client = clients.get(clientID);
        Map<Integer, String> players = new HashMap<>();

        if(waitingLobbies.containsKey(lobbyID)){
            LobbyController lobbyController = waitingLobbies.get(lobbyID);
            lobbyController.addPlayer(clients.get(clientID), playerName, totem);
            Map<VirtualClient, String> lobbyClients = lobbyController.getClients();

            for(VirtualClient virtualClient : lobbyClients.keySet()){
                players.put(virtualClient.getID(),lobbyClients.get(virtualClient));
            }

            client.setLobby(clientID, lobbyID, lobbyController.getPlayerNum(), players, playerName, totem);

        }else{
            System.err.println("Lobby not found or already started");
        }

    }

    @Override
    public synchronized void leaveLobby(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);
        LobbyController lobbyController = waitingLobbies.get(lobbyID);
        lobbyController.removePlayer(clients.get(clientID));

        if(lobbyController.getClients().isEmpty()){
            waitingLobbies.remove(lobbyID);
        }

        client.removeFromLobby(clientID, lobbyID);
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID) {
        if(waitingLobbies.containsKey(lobbyID)){
            LobbyController lobbyController = waitingLobbies.get(lobbyID);
            waitingLobbies.remove(lobbyID,lobbyController);
            runningLobbies.put(lobbyID, lobbyController);

            lobbyController.startLobby();
        }
    }

    @Override
    public synchronized void getWaitingLobbies(int clientID) {
        VirtualClient client = clients.get(clientID);
        List<Lobby> lobbies = new ArrayList<>();

        for(LobbyController lobbyController : waitingLobbies.values()) {
            Lobby lobby = new Lobby(lobbyController.getID(), lobbyController.getPlayerNum());
            lobbies.add(lobby);
        }
        client.setWaitingLobbies(clientID, lobbies);
    }

    @Override
    public synchronized void getLobbyInfo(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);
        Map<Integer, String> players = new HashMap<>();

        if(waitingLobbies.containsKey(lobbyID)){
            LobbyController lobbyController = waitingLobbies.get(lobbyID);
            Map<VirtualClient, String> lobbyClients = lobbyController.getClients();

            for(VirtualClient virtualClient : lobbyClients.keySet()){
                players.put(virtualClient.getID(),lobbyClients.get(virtualClient));
            }

            Lobby lobby = new Lobby(lobbyController.getID(), lobbyController.getPlayerNum(), players);
            client.showLobbyInfo(clientID,lobby);
        }
    }

    @Override
    public synchronized void getRank(int clientID, int lobbyID) {
        LobbyController lobby = runningLobbies.get(lobbyID);
        lobby.showRank(clientID);
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {


    }

    @Override
    public synchronized void requestPick(int clientID, int lobbyID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks) {
        LobbyController lobby = runningLobbies.get(lobbyID);
        lobby.pickCards(clientID, topPicks, bottomPicks);
    }

    public void startServer(String ip, int tcpPort, int rmiPort) {
        try {
            this.networkServer = new NetworkServer(this, ip, tcpPort);
            this.networkServer.start();
            System.out.println("TCP Server started on" + ip + tcpPort);
        } catch (IOException e) {
            System.err.println("Failed to Start TCP Server:" + e.getMessage());
            System.exit(-1);
        }

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("mesos_server", this);
            System.out.println("RMI server started on" + ip + rmiPort);
        } catch (RemoteException e) {
            System.err.println("Failed to start RMI server:" + e.getMessage());
            System.exit(-1);
        }

        System.out.println("Server started");
    }

}