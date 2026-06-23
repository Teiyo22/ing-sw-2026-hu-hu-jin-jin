package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.turn.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Lobby implements Serializable {
    private final int lobbyID;
    private final int size;
    private int playerCount;

    transient private Player shownPlayer = null;
    transient private Map<Player, Boolean> players = null;
    transient private Board board = null;

    transient private TurnState turnState = null;
    transient private LeaderboardResult leaderboard = null;

    public Lobby(int lobbyID, int size, int playerCount) {
        this.lobbyID = lobbyID;
        this.size = size;
        this.playerCount = playerCount;
    }

    //=============================================================================
    // Lobby Management methods
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

    public void reset() {
        players.clear();
        shownPlayer = null;
        turnState = null;
        leaderboard = null;
        board = null;
    }

    //=============================================================================
    // Model management methods
    //=============================================================================

    public void initGame(Collection<Player> players, Board board) {
        this.board = board;
        this.setTribes(players);
    }

    private void setTribes(Collection<Player> players) {
        for (Player player : players) {
            Player matchingPlayer = getPlayerReference(player);

            if (matchingPlayer != null)
                matchingPlayer.setTribe(player.getTribe());
        }
    }

    /** Moves the player from the orderTile to the picked offer tile.
     * @param player the player who picked the offer.
     * @param offerIndex the offer tiles are saved in offerTrack which is an array of offerTile, this indicates the array index of the chosen one.
     * */
    public void resolveOfferPick(Player player, int offerIndex) {
        Player movedPlayer = null;

        for (OrderSlot orderSlot : board.getOrderTile()) {
            if (player.equals(orderSlot.getAssignedPlayer())) {
                movedPlayer = orderSlot.getAssignedPlayer();
                orderSlot.setPlayer(null);
                break;
            }
        }

        if (movedPlayer != null)
            board.getOfferTrack()[offerIndex].setPlayer(movedPlayer);
    }

    public void updateTribe(Player updatedPlayer) {
        Player actionPlayer = getPlayerReference(updatedPlayer);

        if (actionPlayer != null)
            actionPlayer.updateTribe(updatedPlayer.getTribe());
    }

    /** Calls the method pickCards for each set of picks.
     * @param player the player who picks the cards.
     * @param topRowPicks card picks from the top row.
     * @param bottomRowPicks card picks from the bottom row.
     * */
    public void resolveCardPicks(Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        Player actionPlayer = getPlayerReference(player);

        if (actionPlayer == null) return;

        pickCards(actionPlayer, board.getPickable(topRowPicks, true), board.getTopRow());
        pickCards(actionPlayer, board.getPickable(bottomRowPicks, false), board.getBottomRow());

        if (removeFromOfferTrack(actionPlayer))
            addToOrderTile(actionPlayer);
    }

    /** Moves the picked card from the row to the player's hand.
     * @param player the player who picks the cards.
     * @param picks the list of picked cards.
     * @param row the row the cards belong to, where they must be removed.
     * */
    private void pickCards(Player player, List<Pickable> picks, Row row) {
        for (Pickable p : picks) {
            p.onPick(player, null);
            p.removeFrom(row);
        }
    }

    /** Method to find a match between the given player and the players of this lobby.
     * @return the matched player.
     * */
    private Player getPlayerReference(Player player) {
        for (Player p : players.keySet())
            if (p.equals(player))
                return p;
        return null;
    }

    /** Removes a player from its offer tile.
     * @return boolean indicating whether the operation was successful,
     *  needed to know if the player can be moved back to order tile.*/
    private boolean removeFromOfferTrack(Player player) {
        for (int i = 0; i < board.getOfferTrack().length; i++) {
            if (player.equals(board.getOfferTrack()[i].getAssignedPlayer())) {
                board.getOfferTrack()[i].setPlayer(null);
                return true;
            }
        }
        return false;
    }

    /** Adds the player to the first available order slot.*/
    private void addToOrderTile(Player player) {
        for (int i = 0; i < board.getOrderTile().length; i++) {
            if (board.getOrderTile()[i].getAssignedPlayer() == null) {
                board.getOrderTile()[i].setPlayer(player);
                return;
            }
        }
    }

    public void updateRows(Row newTopRow, boolean eraChanged) {
        Row oldTopRow = board.getTopRow();
        Row oldBottomRow = board.getBottomRow();

        if (!eraChanged)
            oldTopRow.setBuildingCards(oldBottomRow.getBuildingCards());

        board.setBottomRow(oldTopRow);
        board.setTopRow(newTopRow);


    }

    public void updateTribes(Collection<Player> updatedPlayers) {
        updatedPlayers.forEach(this::updateTribe);
    }

    public void setRanking(Collection<Player> updatedPlayers) {
        for (Player updatedPlayer : updatedPlayers)
            for (Player player : players.keySet())
                if (player.equals(updatedPlayer))
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

    public Lobby copy() {
        Lobby lobbyCopy = new Lobby(lobbyID, size, playerCount);
        lobbyCopy.setPlayers(getPlayersCopy());
        lobbyCopy.setShownPlayer(getShownPlayerCopy());
        lobbyCopy.setTurnState(turnState);
        lobbyCopy.setLeaderboard(leaderboard);

        return lobbyCopy;
    }

    private Map<Player, Boolean> getPlayersCopy() {
        return players == null ? null :
            players.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().deepCopy(), Map.Entry::getValue));
    }

    private Player getShownPlayerCopy() {
        return shownPlayer == null ? null : shownPlayer.deepCopy();
    }

    public LeaderboardResult getLeaderboard() {
        return leaderboard;
    }

    //=============================================================================
    // Setters
    //=============================================================================

    public void setPlayers(Map<Player, Boolean> players) {
        this.players = players;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setShownPlayer(Player shownPlayer) {
        this.shownPlayer = shownPlayer;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public void setIdleTurnState() {
        this.turnState = new IdleState(turnState.getCurrPlayer(), turnState.getIndex(), turnState.getEra());
    }

    public void setLeaderboard(LeaderboardResult leaderboard) {
        this.leaderboard = leaderboard;
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