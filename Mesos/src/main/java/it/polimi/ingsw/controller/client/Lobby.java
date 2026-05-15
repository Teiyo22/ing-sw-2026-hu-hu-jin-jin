package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.turn.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Lobby implements Serializable {
    private int lobbyID;
    private int size;
    private int playerCount;

    transient private Player shownPlayer = null;
    transient private Map<Player, Boolean> players = null;
    transient private Board board = null;
    transient private TurnState turnState = null;

    public Lobby(int lobbyID, int size, int playerCount) {
        this.lobbyID = lobbyID;
        this.size = size;
        this.playerCount = playerCount;
    }

    //=============================================================================
    // Player Management methods
    //=============================================================================

    public void addPlayer(Player player) {
        players.put(player, true);
    }

    public void removeClient(Player player) {
        if (players.containsKey(player))
            players.put(player, false);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    //=============================================================================
    // Model management methods
    //=============================================================================

    public void initGame(Collection<Player> players, Board board) {
        this.board = board;
        this.updateTribes(players);
    }

    public void updateOrderTile(OrderSlot[] orderTile) {
        this.board.setOrderTile(orderTile);
    }

    public void updateOfferTrack(OfferTile[] offerTrack) {
        this.board.setOfferTrack(offerTrack);
    }

    public void updateTribe(Player updatedPlayer) {
        for (Player player : players.keySet())
            if (player.equals(updatedPlayer))
                player.setTribe(updatedPlayer.getTribe());
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void updateTopRow(Row row) {
        this.board.setTopRow(row);
    }

    public void updateBottomRow(Row row) {
        this.board.setBottomRow(row);
    }

    public void updateTribes(Collection<Player> updatedPlayers) {
        updatedPlayers.forEach(this::updateTribe);
    }

    public void setRanking(Collection<Player> updatedPlayers) {
        for (Player updatedPlayer : updatedPlayers)
            for (Player player : players.keySet())
                if (player.equals(updatedPlayers))
                    player.setRank(updatedPlayer.getRank());
    }

    public void showPlayer(Player player) {
        shownPlayer = player;
    }

    public void hidePlayer() {
        shownPlayer = null;
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public int getLobbyID() {
        return lobbyID;
    }

    public int getSize() {
        return size;
    }

    public Map<Player, Boolean> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Player getShownPlayer() {
        return shownPlayer;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public Player getPlayer(String clientID) {
        for (Player player : players.keySet())
            if (clientID.equals(player.getName()))
                return player;
        return null;
    }

    public Player getCurrPlayer() {
        if (turnState == null)
            return null;

        for (Player player : players.keySet())
            if (player.equals(turnState.getCurrPlayer()))
                return player;

        return null;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public boolean containsClient(String clientID) {
        for (Player player : players.keySet())
            if (clientID.equals(player.getName()) && players.get(player))
                return true;
        return false;
    }

    public boolean isShownPlayer() {
        return shownPlayer != null;
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public void setPlayers(Map<Player, Boolean> players) {
        this.players = players;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public void setIdleTurnState() {
        this.turnState = new IdleState(turnState.getCurrPlayer(), turnState.getIndex(), getBoard().getDeck().getCurrentEra());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Lobby)) return false;

        Lobby other = (Lobby) obj;
        return lobbyID == other.lobbyID;
    }
}