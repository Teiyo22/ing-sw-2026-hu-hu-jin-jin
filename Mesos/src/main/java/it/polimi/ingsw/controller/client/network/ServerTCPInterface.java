package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.player.Totem;

public class ServerTCPInterface {
    private ClientController clientController;
    private NetworkClient serverHandler;

    public void addCLient(VirtualClient client){

    }

    public void createLobby(int clientID, int playerNum, String playerName, Totem totem){

    }

    public void joinLobby(int clientID, int lobbyID, String playerName, Totem totem){

    }

    public void leaveLobby(int clientID, int lobbyID){

    }

    public void startLobby(int clientID, int lobbyID){

    }

    public void startLobby(int lobbyID){

    }

    public List<Lobby> getWaitingLobbies(int clientID){

    }

    public Lobby getLobbyInfo(int clientID, int lobbyID){

    }
}
