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

    public static ServerController getInstance() {
        return instance;
    }

    public void broadcast(Response response){

    }

    @Override
    public void addClient(VirtualClient client) {
        int clientID = nextClientID++;
        clients.put(clientID, client);
    }

    @Override
    public void createLobby(int clientID, int playerNum, String playerName, Totem totem) {
        VirtualClient client = clients.get(clientID);
        Map<Integer, String> players = new HashMap<>();
        int lobbyID= nextLobbyID++;
        LobbyController lobbyController = new LobbyController(clientID, lobbyID, playerNum, playerName, totem);
        players.put(clientID, playerName);
        waitingLobbies.put(lobbyID, lobbyController);
        client.setLobby(clientID, lobbyID, playerNum, players, playerName, totem);
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, String playerName, Totem totem) {
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
    public void leaveLobby(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);
        LobbyController lobby = waitingLobbies.get(lobbyID);
        lobby.removePlayer(clients.get(clientID));
        if(lobby.getClients().size() == 0){
            waitingLobbies.remove(lobbyID);
        }
        client.removeFromLobby(clientID, lobbyID);
    }

    @Override
    public void startLobby(int clientID, int lobbyID) {
        if(waitingLobbies.containsKey(lobbyID)){
            LobbyController lobby = waitingLobbies.get(lobbyID);
            waitingLobbies.remove(lobbyID,lobby);
            runningLobbies.put(lobbyID, lobby);
            Game game = new Game(playerConfig);
            lobby.setModel(game);

        }
    }

    @Override
    public void getWaitingLobbies(int clientID) {
        VirtualClient client = clients.get(clientID);
        List<Lobby> lobbies = new ArrayList<>();

        for(LobbyController lobbyController : waitingLobbies.values()) {
            Lobby lobby = new Lobby(lobbyController.getID(), lobbyController.getSize());
            lobbies.add(lobby);
        }
        client.setWaitingLobbies(clientID, lobbies);
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);

        if(waitingLobbies.containsKey(lobbyID)){
            LobbyController lobbyController = waitingLobbies.get(lobbyID);

            for(VirtualClient virtualClient : lobbyClients.keySet()){
                players.put(virtualClient.getID(),lobbyClients.get(virtualClient));
            }

            Lobby lobby = new Lobby(lobbyController.getID(), lobbyController.getSize(), players);
            client.showLobbyInfo(clientID,lobby);
        }
    }

    @Override
    public void getRank(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);
        LobbyController lobby = runningLobbies.get(lobbyID);
        client.showRank(lobbyID, lobby.getRank(clientID));
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum){


    }

    @Override
    public void requestPick(int clientID, int lobbyID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks){
        VirtualClient client = clients.get(clientID);
        runningLobbies.get(lobbyID).pickCards(clientID, topPicks, bottomPicks);
        client.confirmPick(clientID, topPicks, bottomPicks);
    }

    public void start(String registryName, String ip, int tcpPort, int rmiPort) throws IOException {
        this.networkServer = new NetworkServer(this, ip, tcpPort);
        this.networkServer.start();
        System.out.println("RMI Server started on" + ip + tcpPort);

        setUpRMI(rmiPort, registryName);
        System.out.println("RMI server started on" + ip + rmiPort);
    }

    public void setUpRMI(int rmiPort, String registryName){
        try{
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(this,0);
            registry.rebind(registryName, stub);
            System.out.println("Server registered on" + registryName);
        }catch (RemoteException e){
            System.err.println("Error RMI:" + e.getMessage());
        }
    }
}