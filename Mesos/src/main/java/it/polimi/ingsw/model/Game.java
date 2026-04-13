    package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import java.util.ArrayList;

import java.util.List;

public class Game {
    private final PlayerConfig playerConfig;
    private final List<Player> players;
    private final Board board;
    private final List<Player> leaderboard;

    private BuildingHandler buildingHandler;
    private GameState gameState;

    public Game(PlayerConfig playerConfig) {
        this.playerConfig = playerConfig;
        this.players = new ArrayList<Player>();
        this.board = new Board(this);
        this.leaderboard = new ArrayList<Player>();
        this.buildingHandler = new BuildingHandler();

        this.gameState = new GameStartState(this, this.buildingHandler);
        this.board.initOfferTrack();
        this.board.initOrderTile();
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

    public void addPlayer(Totem totem, String name) {
        Player player = new Player(name, totem);
        players.add(player);
        gameState.update();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public GameState getGameState() {
        return gameState;
    }
}
