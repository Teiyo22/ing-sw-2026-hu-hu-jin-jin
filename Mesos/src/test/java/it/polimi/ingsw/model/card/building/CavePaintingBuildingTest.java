package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CavePaintingBuildingTest {
    private CavePaintingBuilding cavePaintingBuilding;
    private Player p;

    @BeforeEach
    void setUp(){
        cavePaintingBuilding= new CavePaintingBuilding("CavePaintingBuilding",1,false, 3, 10,new BuildingHandler(), 4);
        p= new Player("Pipino", Totem.BLUE);
    }

    @Test
    void applyEffectTest(){
        cavePaintingBuilding.onPick(p,new BuildingHandler());
        cavePaintingBuilding.applyEffect();
        assertEquals(0,p.getFood(),"ho 0 artisti");

        p.getTribe().addArtist();
        cavePaintingBuilding.applyEffect();
        assertEquals(4,p.getFood(),"ho 1 artista");
    }
}
