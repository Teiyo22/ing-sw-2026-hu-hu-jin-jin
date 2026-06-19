package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraActionBuildingTest {
    private ExtraActionBuilding extraActionBuilding;
    private List<Player> players;
    private ExtraActionState extraActionState;
    private BuildingHandler handler;

    @BeforeEach
    void setUp(){
        extraActionBuilding = new ExtraActionBuilding("ExtraActionBuilding",1,false,2,3);
        players = new ArrayList<>();

        players.add(new Player("Marco", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));

        handler = new BuildingHandler();

    }

    @Test
    void applyEffectTest(){
        extraActionState= new ExtraActionState(new Game(PlayerConfig.TWO, players), new BuildingHandler());

        extraActionBuilding.register(players.get(0), handler);

        handler.applyExtraActionEffects(extraActionState, 0);
        assertEquals(players.get(0), extraActionState.getCurrPlayer());
    }
}
