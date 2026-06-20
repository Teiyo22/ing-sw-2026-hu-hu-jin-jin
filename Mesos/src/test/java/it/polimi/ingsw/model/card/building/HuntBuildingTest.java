package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.character.Hunter;
import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HuntBuildingTest {
    private HuntBuilding huntBuilding;
    private Player p;
    private BuildingHandler handler;


    @BeforeEach
    void setUp(){
        huntBuilding = new HuntBuilding(1, false, 3,2, 1, 1);
        p= new Player("Pallino", Totem.RED);
        p.setTribe(new Tribe());

        handler = new BuildingHandler();
        huntBuilding.register(p, handler);
    }

    @Test
    void applyEffectTest(){
        handler.applyHuntEffects();
        assertEquals(0,p.getFood());
        assertEquals(0,p.getPP());


        Hunter hunter1 = new Hunter(1, false, false);
        Hunter hunter2 = new Hunter(1, false, false);


        p.getTribe().addHunter(hunter1);
        handler.applyHuntEffects();
        assertEquals(1,p.getPP());
        assertEquals(1,p.getFood());

        p.getTribe().addHunter(hunter2);
        handler.applyHuntEffects();
        assertEquals(3,p.getPP());
        assertEquals(3,p.getFood());
    }
}
