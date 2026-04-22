package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.ServerTCPInterface;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Totem;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientController extends VirtualClient{
    private VirtualServer server;

    public ClientController() {
        this.server = null;
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {

    }

    @Override
    public void showLobbyInfo(int clientID, Lobby lobby) {

    }

    @Override
    public void setLobby(int clientID, int lobbyID, String playerName, Totem totem) {

    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {

    }

    @Override
    public void showRank(int clientID, Map<Integer, Integer> rankings) {

    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public void confirmPick(int clientID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks) {

    }


    /** Connecting to the server using RMI.
     * @param registryName the name of the server in the registry.
     * */
    public void connectRMI(String registryName, String ip, int rmiPort){
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            this.server = (VirtualServer) registry.lookup(registryName);
            server.addClient(this);
        } catch (RemoteException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
        }
    }

    /** Connecting to the server using TCP.
     * Creates the NetworkClient and the ServerTCPInterface, which initializes the server reference in the first.
     * */
    public void connectTCP(String ip, int tcpPort) {
        NetworkClient networkClient = new NetworkClient();
        this.server = new ServerTCPInterface(this, networkClient);
        try {
            networkClient.connect(ip, tcpPort);
        } catch (UnknownHostException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        }
    }
}
