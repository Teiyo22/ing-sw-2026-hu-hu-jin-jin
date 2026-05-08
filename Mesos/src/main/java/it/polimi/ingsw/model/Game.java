    package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.List;
import java.util.stream.Stream;

    public class Game {
    private final LobbyController lobbyController;

    private final PlayerConfig playerConfig;
    private final List<Player> players;
    private final Board board;

    private GameState gameState;
    private final BuildingHandler buildingHandler;

    public Game(LobbyController lobbyController, PlayerConfig playerConfig, List<Player> players) {
        this.lobbyController = lobbyController;
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

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * Picks the cards for a player and updates the game state.
     * @param player is the player that picked the cards.
     * @param topPicks are the cards picked from the top row.
     * @param bottomPicks are the cards picked from the bottom row.
     * */
    public void pick(Player player, List<Integer> topPicks, List<Integer> bottomPicks) {
        List<Pickable> top = Stream.concat(
                board.getTopRow().getBuildingCards().stream()
                .filter(b -> topPicks.contains(b.getID()))
                .map(b -> (Pickable) b),
                board.getTopRow().getBuildingCards().stream()
                .filter(b -> topPicks.contains(b.getID()))
                .map(b -> (Pickable) b))
                .toList();

        for(Pickable p: top){
            p.onPick(player, buildingHandler);
            p.removeFrom(board.getTopRow());
        }

        List<Pickable> bottom = Stream.concat(
                 board.getBottomRow().getBuildingCards().stream()
                 .filter(b -> bottomPicks.contains(b.getID()))
                 .map(b -> (Pickable) b),
                 board.getBottomRow().getBuildingCards().stream()
                 .filter(b -> bottomPicks.contains(b.getID()))
                 .map(b -> (Pickable) b))
                .toList();

        for(Pickable p: bottom){
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
