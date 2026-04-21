package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.model.card.character.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvetorPairBuildingTest {
    InventorPairBuilding inventorPairBuilding;
    BuildingHandler buildingHandler;
    Player p;

    @BeforeEach
    void setUp(){
        inventorPairBuilding=new InventorPairBuilding("InventorPairBuilding", 1, false, 2, 2, new BuildingHandler());
        buildingHandler= new BuildingHandler();
        p= new Player("Gigio", Totem.BLACK);
    }

    @Test
    void doForInventorTest(){
        Inventor inventor= new Inventor("Inventor", 1, false, InventorType.BAKER);

        p.getTribe().addInventor(inventor);
        inventorPairBuilding.onPick(p,buildingHandler);
        inventorPairBuilding.doForInventor(inventor);
        assertEquals(0,p.getFood());

        p.getTribe().addInventor(inventor);
        inventorPairBuilding.doForInventor(inventor);
        assertEquals(3,p.getFood());

        p.getTribe().addInventor(inventor);
        inventorPairBuilding.doForInventor(inventor);
        assertEquals(3,p.getFood());
    }



}
