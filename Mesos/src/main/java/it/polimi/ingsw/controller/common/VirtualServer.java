package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Totem;

import java.rmi.RemoteException;

import java.rmi.Remote;
import java.util.Set;

public interface VirtualServer extends Remote {
    void registerClient(ClientInterface client) throws RemoteException;
    void login(String clientID, String username) throws RemoteException;
    void getLobbyInfo(String clientID, int lobbyID) throws RemoteException;
    void createLobby(String clientID, int playerNum, Totem totem) throws RemoteException;
    void joinLobby(String clientID, int lobbyID, Totem totem) throws RemoteException;
    void leaveLobby(String clientID, int lobbyID) throws RemoteException;
    void startLobby(String clientID, int lobbyID) throws RemoteException;
    void requestAction(String clientID, int lobbyID, PlayerAction action) throws RemoteException;
    void getLeaderboard(String clientID, int playerNum) throws RemoteException;
    void ping(String clientID) throws RemoteException;
}