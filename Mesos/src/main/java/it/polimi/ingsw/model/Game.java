package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Game {
    private LobbyRunningState lobbyState = null;

    private final PlayerConfig playerConfig;
    private final List<Player> players;
    private final Board board;

    private GameState gameState;
    private final BuildingHandler buildingHandler;

    public Game(PlayerConfig playerConfig, List<Player> players) {
        this.playerConfig = playerConfig;
        this.players = players;

        Logger.getInstance().print(LoggerLevel.DEBUG, "Initializing board");

        this.board = new Board(this);
        this.board.initOfferTrack();
        this.board.initOrderTile();

        Logger.getInstance().print(LoggerLevel.DEBUG, "Board initialized");

        this.buildingHandler = new BuildingHandler();
        this.gameState = new GameStartState(this, buildingHandler);
        this.gameState.update();
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public GameState getGamestate() {
        return gameState;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public LobbyRunningState getLobbyState() {
        return lobbyState;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public void setLobbyState(LobbyRunningState lobbyState) {
        this.lobbyState = lobbyState;
    }

    /**
     * Picks the cards for a player and updates the game state.
     *
     * @param player      is the player that picked the cards.
     * @param topPicks    are the cards picked from the top row.
     * @param bottomPicks are the cards picked from the bottom row.
     *
     */
    public void pick(Player player, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        for (Pickable p : board.getPickable(topPicks, true)) {
            p.onPick(player, buildingHandler);
            p.removeFrom(board.getTopRow());
        }

        for (Pickable p : board.getPickable(bottomPicks, false)) {
            p.onPick(player, buildingHandler);
            p.removeFrom(board.getBottomRow());
        }

        lobbyState.notifyOfferResolution(player, topPicks, bottomPicks);
        gameState.update();
    }

    /**
     * Assigns the player to the selected offer tile and updates the game state.
     *
     * @param player is the player that picked the offer tile.
     * @param offerIndex  is the index of the offer tile that the player picked.
     *
     */
    public void assignTo(Player player, int offerIndex) {
        board.getOfferTrack()[offerIndex].setPlayer(player);

        lobbyState.notifyOfferPick(player, offerIndex);
        gameState.update();
    }


}
