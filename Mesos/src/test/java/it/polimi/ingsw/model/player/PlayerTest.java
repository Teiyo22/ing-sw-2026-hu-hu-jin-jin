package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.CavePaintingBuilding;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.card.building.AbstractBuilding;

public class PlayerTest {
    private Player p;

    @BeforeEach
    public void setUp(){
        p= new Player("Pino",Totem.BLACK);
    }


    @Test
    void addPPTest(){
        p.addPP(6);
        assertEquals(6,p.getPP());
    }

    @Test
    void addFoodTest(){
        p.addFood(7);
        assertEquals(7,p.getFood());
    }

    @Test
    void enableNoLossRitualMod(){
        p.enableNoLossRitualMod();
        assertTrue(p.getNoLossRitualMod());
    }

    @Test
    void addBuildingTest(){
        BuildingHandler buildinghandler= new BuildingHandler();
        AbstractBuilding b= new CavePaintingBuilding("CavePaintingBuilding",1,false,5,2,buildinghandler,2);
        p.addBuilding(b);
        assertEquals(1,p.getBuildings().size());

    }

    @Test
    void enableDoubleRitualModTest(){
        p.enableDoubleRitualMod();
        assertTrue(p.getDoubleRitualMod());
    }

    @Test
    void getTribeTest(){
        assertNotNull(p.getTribe()); //non sono sicuro per il get in verità manco per gli altri
    }

    @Test
    void setRankTest(){
        p.setRank(67);
        assertEquals(67,p.getRank());
    }


}
