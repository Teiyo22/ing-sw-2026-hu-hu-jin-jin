package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.InventorPairBuilding;
import it.polimi.ingsw.model.card.character.Artist;
import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEndStateTest {
    private Game g;
    private List<Player> players;
    private GameEndState gameEndState;

    @Test
    void setLeaderboardTest() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));

        g = new Game(PlayerConfig.FOUR, players);
        g.setLobbyState(new LobbyRunningState(new LobbyController(1, 2)));

        gameEndState = new GameEndState(g, new BuildingHandler());
        g.setGameState(gameEndState);

        Player p0 = players.get(0);
        Player p1 = players.get(1);
        Player p2 = players.get(2);
        Player p3 = players.get(3);

        p0.setPP(10);
        p1.setPP(10);
        p2.setPP(10);
        p3.setPP(9);
        p0.setFood(10);
        p1.setFood(10);
        p2.setFood(9);
        p3.setFood(100);

        gameEndState.update();

        assertEquals(1, p0.getRank());
        assertEquals(1, p1.getRank());
        assertEquals(3, p2.getRank());
        assertEquals(4, p3.getRank());
    }

    @Test
    void assignBonusPPTest() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));

        g = new Game(PlayerConfig.FOUR, players);
        g.setLobbyState(new LobbyRunningState(new LobbyController(1, 2)));

        gameEndState = new GameEndState(g, new BuildingHandler());
        g.setGameState(gameEndState);

        Player p0 = players.get(0);
        Player p1 = players.get(1);
        Player p2 = players.get(2);
        Player p3 = players.get(3);

        p0.setPP(0);
        p1.setPP(0);
        p2.setPP(0);
        p3.setPP(0);
        p0.setFood(0);
        p1.setFood(0);
        p2.setFood(0);
        p3.setFood(0);

        p0.getTribe().addBuilder(new Builder(1, false, 2, 2));
        p1.getTribe().addInventor(new Inventor(1, false, InventorType.FLETCHER));
        p1.getTribe().addInventor(new Inventor(1, false, InventorType.FLETCHER));
        p1.getTribe().addInventor(new Inventor(1, false, InventorType.FISHERMAN));
        p2.getTribe().addArtist(new Artist(1, false));
        p2.getTribe().addArtist(new Artist(1, false));
        p3.getTribe().addBuilding(new InventorPairBuilding(1, false, 0, 2));

        g.getBoard().getTopRow().getEventCards().clear();
        g.getBoard().getTopRow().getSustenanceEventCards().clear();
        g.getBoard().getBottomRow().getEventCards().clear();
        g.getBoard().getBottomRow().getSustenanceEventCards().clear();

        gameEndState.update();

        assertEquals(2, p0.getPP());
        assertEquals(6, p1.getPP());
        assertEquals(10, p2.getPP());
        assertEquals(2, p3.getPP());
    }
}