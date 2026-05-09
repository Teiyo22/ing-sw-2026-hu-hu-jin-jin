package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;

import java.rmi.Remote;
import java.util.Set;

public interface VirtualServer extends Remote {
    void addClient(VirtualClient client) throws RemoteException;
    void createLobby(int clientID, int playerNum, Player player) throws RemoteException;
    void joinLobby(int clientID, int lobbyID, Player player) throws RemoteException;
    void leaveLobby(int clientID, int lobbyID) throws RemoteException;
    void startLobby(int clientID, int lobbyID) throws RemoteException;
    void getWaitingLobbies(int clientID) throws RemoteException;
    void getLobbyInfo(int clientID, int lobbyID) throws RemoteException;
    void getLeaderboard(int clientID, int playerNum) throws RemoteException;
    void requestCards(int clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks) throws RemoteException;
    void requestOffer(int clientID, int lobbyID, int offerIndex) throws RemoteException;

    void ping(int clientID) throws RemoteException;
}