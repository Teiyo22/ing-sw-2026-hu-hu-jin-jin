package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;
import java.util.List;

public class RMIServerInterface extends ServerInterface {
    VirtualServer wrappedServer;

    public RMIServerInterface(ClientController clientController, VirtualServer wrappedServer) {
        super(clientController);
        this.wrappedServer = wrappedServer;
    }

    @Override
    public void addClient(VirtualClient client){
        try {
            wrappedServer.addClient(client);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void createLobby(int clientID, int playerNum, Player player){
        try {
            wrappedServer.createLobby(clientID, playerNum, player);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, Player player){
        try {
            wrappedServer.joinLobby(clientID, lobbyID, player);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void leaveLobby(int clientID, int lobbyID){
        try {
            wrappedServer.leaveLobby(clientID, lobbyID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID){
        try {
            wrappedServer.startLobby(clientID, lobbyID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void getWaitingLobbies(int clientID){
        try {
            wrappedServer.getWaitingLobbies(clientID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID){
        try {
            wrappedServer.getLobbyInfo(clientID, lobbyID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void getRank(int clientID, int lobbyID){
        try {
            wrappedServer.getRank(clientID, lobbyID);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum){
        try {
            wrappedServer.getLeaderboard(clientID, playerNum);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void requestCards(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks){
        try {
            wrappedServer.requestCards(clientID, lobbyID, topPicks, bottomPicks);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void requestOffer(int clientID, int lobbyID, int offerIndex){
        try {
            wrappedServer.requestOffer(clientID, lobbyID, offerIndex);
        } catch (RemoteException e) {

        }
    }
}
