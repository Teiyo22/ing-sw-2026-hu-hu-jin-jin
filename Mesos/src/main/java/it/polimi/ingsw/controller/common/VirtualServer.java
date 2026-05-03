package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;
import java.util.List;

import java.rmi.Remote;

public interface VirtualServer extends Remote {
    public abstract void addClient(VirtualClient client) throws RemoteException;
    public abstract void createLobby(int clientID, int playerNum, Player player) throws RemoteException;
    public abstract void joinLobby(int clientID, int lobbyID, Player player) throws RemoteException;
    public abstract void leaveLobby(int clientID, int lobbyID) throws RemoteException;
    public abstract void startLobby(int clientID, int lobbyID) throws RemoteException;
    public abstract void getWaitingLobbies(int clientID) throws RemoteException;
    public abstract void getLobbyInfo(int clientID, int lobbyID) throws RemoteException;
    public abstract void getRank(int clientID, int lobbyID) throws RemoteException;
    public abstract void getLeaderboard(int clientID, int playerNum) throws RemoteException;
    public abstract void requestCards(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks) throws RemoteException;
    public abstract void requestOffer(int clientID, int lobbyID, int offerIndex) throws RemoteException;
}