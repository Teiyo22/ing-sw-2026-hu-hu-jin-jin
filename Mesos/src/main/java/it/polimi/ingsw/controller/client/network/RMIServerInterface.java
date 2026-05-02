package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RMIServerInterface extends ServerInterface {
    VirtualServer wrappedServer;

    int maxFails = 10;
    AtomicInteger failsCounter = new AtomicInteger(0);

    public RMIServerInterface(ClientController clientController, VirtualServer wrappedServer) {
        super(clientController);
        this.wrappedServer = wrappedServer;
    }

    @Override
    public void addClient(VirtualClient client){
        try {
            wrappedServer.addClient(client);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> { addClient(client); });
        }
    }

    @Override
    public void createLobby(int clientID, int playerNum, Player player){
        try {
            wrappedServer.createLobby(clientID, playerNum, player);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {createLobby(clientID, playerNum, player);});
        }
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, Player player){
        try {
            wrappedServer.joinLobby(clientID, lobbyID, player);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {joinLobby(clientID, lobbyID, player);});
        }
    }

    @Override
    public void leaveLobby(int clientID, int lobbyID){
        try {
            wrappedServer.leaveLobby(clientID, lobbyID);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {leaveLobby(clientID, lobbyID);});
        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID){
        try {
            wrappedServer.startLobby(clientID, lobbyID);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {startLobby(clientID, lobbyID);});
        }
    }

    @Override
    public void getWaitingLobbies(int clientID){
        try {
            wrappedServer.getWaitingLobbies(clientID);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {getWaitingLobbies(clientID);});
        }
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID){
        try {
            wrappedServer.getLobbyInfo(clientID, lobbyID);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {getLobbyInfo(clientID, lobbyID);});
        }
    }

    @Override
    public void getRank(int clientID, int lobbyID){
        try {
            wrappedServer.getRank(clientID, lobbyID);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {getRank(clientID, lobbyID);});
        }
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum){
        try {
            wrappedServer.getLeaderboard(clientID, playerNum);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {getLeaderboard(clientID, playerNum);});
        }
    }

    @Override
    public void requestCards(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks){
        try {
            wrappedServer.requestCards(clientID, lobbyID, topPicks, bottomPicks);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {requestCards(clientID, lobbyID, topPicks, bottomPicks);});
        }
    }

    @Override
    public void requestOffer(int clientID, int lobbyID, int offerIndex){
        try {
            wrappedServer.requestOffer(clientID, lobbyID, offerIndex);
            failsCounter.set(0);
        } catch (RemoteException e) {
            if (failsCounter.incrementAndGet() >= maxFails)
                clientController.close();
            else
                clientController.scheduleRetry(() -> {requestOffer(clientID, lobbyID, offerIndex);});
        }
    }

    @Override
    public void disconnect() {
        try {
            UnicastRemoteObject.unexportObject(clientController, true);
        } catch (RemoteException e) {
        }
    }
}
