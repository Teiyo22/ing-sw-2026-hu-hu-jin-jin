package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.ExtraActionBuilding;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtraActionStateTest {
    ExtraActionState extraActionState;
    private Game g;
    private List<Player> players;
    private BuildingHandler buildingHandler;

    @Test
    void updateTest () {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));

        g = new Game(PlayerConfig.FOUR, players);

        g.setLobbyState(new LobbyRunningState(new LobbyController(1, 4)));

        buildingHandler = new BuildingHandler();
        extraActionState = new ExtraActionState(g, buildingHandler);


        ExtraActionBuilding extraActionBuilding = new ExtraActionBuilding(1, false, 0, 0);
        extraActionBuilding.onPick(players.get(0), buildingHandler);

        g.setGameState(extraActionState);

        extraActionState.update();
        assertEquals(players.get(0), extraActionState.getCurrPlayer());
        assertEquals(ExtraActionState.class, g.getGameState().getClass());
        extraActionState.update();
        assertEquals(RoundStartState.class, g.getGameState().getClass());
    }
}