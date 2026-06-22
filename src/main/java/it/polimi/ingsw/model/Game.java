package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;

import java.util.List;
import java.util.Set;

public class Game {
    transient private LobbyRunningState lobbyState = null;

    transient private PlayerConfig playerConfig;
    transient private BuildingHandler buildingHandler;

    private GameState gameState;
    private List<Player> players;
    private Board board;

    public Game(PlayerConfig playerConfig, List<Player> players) {
        this.playerConfig = playerConfig;
        this.players = players;
        this.board = new Board(this);
        this.buildingHandler = new BuildingHandler();

        this.gameState = new GameStartState(this, buildingHandler);
        this.gameState.update();
    }

    public Game(List<Player> players, Board board, GameState gameState) {
        this.players = players;
        this.board = board;
        this.gameState = gameState;
    }

    public Game snapshot() {
        return new Game(
            players.stream().map(Player::deepCopy).toList(),
            board.deepCopy(),
            gameState.copy());
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public GameState getGamestate() {
        return gameState;
    }

    public BuildingHandler getBuildingHandler() {
        return buildingHandler;
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

    /** Builds the model again after being loaded by a save.
     * Everything reflects the saved conditions.*/
    public void build() {
        playerConfig = PlayerConfig.getPlayerConfig(players.size());
        buildingHandler = new BuildingHandler();
        gameState.fixReferences(this);
        board.fixReferences(players);
        players.forEach(p -> p.registerBuildings(buildingHandler));
    }
}
