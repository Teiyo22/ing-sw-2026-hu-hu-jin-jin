package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import java.util.List;

public class Game {
    private final int gameSize;
    private final List<Player> players;
    private final Board board;
    private final List<Player> leaderboard;

    private GameState gameState;

    public Game(int gameSize) {
        this.gameSize = gameSize;
        this.players = new ArrayList<Player>();
        this.board = new Board();
        this.leaderboard = new ArrayList<Player>();

        this.gameState = new GameStartState();
    }

    public int getGameSize() {
        return gameSize;
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
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }
}
