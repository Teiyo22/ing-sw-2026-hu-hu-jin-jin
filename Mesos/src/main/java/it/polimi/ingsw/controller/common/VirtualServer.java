package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;

import java.rmi.Remote;
import java.util.Set;

public interface VirtualServer extends Remote {
    void registerClient(ClientInterface client) throws RemoteException;
    void login(String clientID, String username) throws RemoteException;
    void getWaitingLobbies(String clientID) throws RemoteException;
    void getLobbyInfo(String clientID, int lobbyID) throws RemoteException;
    void createLobby(String clientID, int playerNum, Player player) throws RemoteException;
    void joinLobby(String clientID, int lobbyID, Player player) throws RemoteException;
    void leaveLobby(String clientID, int lobbyID) throws RemoteException;
    void startLobby(String clientID, int lobbyID) throws RemoteException;
    void requestCards(String clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks) throws RemoteException;
    void requestOffer(String clientID, int lobbyID, int offerIndex) throws RemoteException;
    void getLeaderboard(String clientID, int playerNum) throws RemoteException;
    void ping(String clientID) throws RemoteException;
}