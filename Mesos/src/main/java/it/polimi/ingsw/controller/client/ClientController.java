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

    public void connectRMI(String registryName, String ip, int rmiPort){

    }

    public void connectTCP(String ip, int tcpPort) throws UnknownHostException, IOException {
        NetworkClient networkClient = new NetworkClient();
        networkClient.connect(ip, tcpPort);
        ServerTCPInterface serverTCPInterface= new ServerTCPInterface(this, networkClient);
        networkClient.setServer(serverTCPInterface);
        this.server = serverTCPInterface;
    }
}
