package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;

import java.util.List;
import java.util.Map;

public class ClientController extends VirtualClient{
    private VirtualServer server;

    public ClientController() {

    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {

    }

    @Override
    public void showLobbyInfo(int clientID, Lobby lobby) {

    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        synchronized (lobbiesLock) {
            if (waitingLobbies.containsKey(lobbyID)) {
                currLobby = waitingLobbies.get(lobbyID);
                currLobby.addPlayer(clientID, player);
            }
        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {

    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {

    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public void confirmPick(int clientID, Row updatedTopRow, Row updatedBottomRow, Tribe updatedTribe) {

    }

    public void connectRMI(String registryName, String ip, int rmiPort){

    }

    public void connectTCP(String ip, int tcpPort){

    }
}
