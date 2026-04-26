    package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.Pickable;
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

    private GameState gameState;
    private final BuildingHandler buildingHandler;

    public Game(PlayerConfig playerConfig, List<Player> players) {
        this.playerConfig = playerConfig;
        this.players = players;
        this.board = new Board(this);
        this.board.initOfferTrack();
        this.board.initOrderTile();

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


    /**
     * Picks the cards for a player and updates the game state.
     * @param player is the player that picked the cards.
     * @param topPicks are the cards picked from the top row.
     * @param bottomPicks are the cards picked from the bottom row.
     * */
    private void pick(Player player, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        for(Pickable p: topPicks){
            p.onPick(player, buildingHandler);
            p.removeFrom(board.getTopRow());
        }

        for(Pickable p: bottomPicks){
            p.onPick(player, buildingHandler);
            p.removeFrom(board.getBottomRow());
        }

        gameState.update();
    }

    /**
     * Assigns the player to the selected offer tile and updates the game state.
     * @param player is the player that picked the offer tile.
     * @param offer is the offer tile that the player picked.
     * */
    public void assignTo(Player player, OfferTile offer){
        offer.setPlayer(player);
        gameState.update();
    }
}
