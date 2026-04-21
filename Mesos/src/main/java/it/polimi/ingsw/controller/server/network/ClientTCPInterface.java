package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.player.Totem;

import java.util.List;
import java.util.Map;

public class ClientTCPInterface extends VirtualClient {
    private ClientHandler clientHandler;

    public ClientTCPInterface(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
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
}