package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.character.Artist;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CavePaintingBuildingTest {
    private CavePaintingBuilding cavePaintingBuilding;
    private Player p;
    BuildingHandler handler;

    @BeforeEach
    void setUp(){
        cavePaintingBuilding= new CavePaintingBuilding("CavePaintingBuilding",1,false, 0, 0, 1);
        p= new Player("Pipino", Totem.BLUE);
        p.setTribe(new Tribe());

        handler = new BuildingHandler();
        cavePaintingBuilding.register(p, handler);
    }

    @Test
    void applyEffectTest(){
        cavePaintingBuilding.onPick(p,new BuildingHandler());
        handler.applyCavePaintingEffects();
        assertEquals(0,p.getFood(),"ho 0 artisti");

        Artist artist = new Artist("Artist", 1, false);

        p.getTribe().addArtist(artist);
        handler.applyCavePaintingEffects();
        assertEquals(1,p.getFood(),"ho 1 artista");  //????
    }
}
