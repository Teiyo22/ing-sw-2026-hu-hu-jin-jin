package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraActionBuildingTest {
    private ExtraActionBuilding extraActionBuilding;
    private Player p;
    private ExtraActionState extraActionState;

    @BeforeEach
    void setUp(){
        extraActionBuilding = new ExtraActionBuilding("ExtraActionBuilding",1,false,2,3);
        p=new Player("Ciccio", Totem.BLACK);
    }

    @Test
    void applyEffectTest(){
        extraActionState= new ExtraActionState(new Game(PlayerConfig.TWO), new BuildingHandler());
        extraActionBuilding.applyEffect(extraActionState);
        assertEquals(p,extraActionState.getCurrPlayer());
    }
}
