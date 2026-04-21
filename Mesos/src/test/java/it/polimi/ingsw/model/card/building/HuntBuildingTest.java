package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HuntBuildingTest {
    private HuntBuilding huntBuilding;
    private Player p;

    @BeforeEach
    void setUp(){
        huntBuilding = new HuntBuilding("HuntBuilding", 1, false, 3,2, new BuildingHandler(), 2, 4);
        p= new Player("Pallino", Totem.RED);
    }

    @Test
    void applyEffectTest(){
        huntBuilding.onPick(p,new BuildingHandler());
        huntBuilding.applyEffect();
        assertEquals(0,p.getFood());
        assertEquals(0,p.getPP());

        p.getTribe().addHunter();
        huntBuilding.applyEffect();
        assertEquals(2,p.getPP());
        assertEquals(4,p.getFood());

        p.getTribe().addHunter();
        huntBuilding.applyEffect();
        assertEquals(6,p.getPP());
        assertEquals(12,p.getFood());
    }
}
