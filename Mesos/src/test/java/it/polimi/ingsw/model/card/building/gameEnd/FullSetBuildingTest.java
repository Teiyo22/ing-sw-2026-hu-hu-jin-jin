package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.FullSetBuilding;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Totem;

import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullSetBuildingTest {
    private BuildingHandler buildingHandler;
    private FullSetBuilding building;
    private Player player;

    @BeforeEach
    void setup(){
        buildingHandler = new BuildingHandler();
        building = new FullSetBuilding("FullSetBuilding",1 , false, 0, 0);
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());

        building.register(player, buildingHandler);
    }

    @Test
    void testApplyEffect(){
        buildingHandler.applyGameEndEffects();
        assertEquals(0, player.getPP());

        player.getTribe().addBuilder(new Builder("Builder", 1, false, 2, 2));
        player.getTribe().addArtist(new Artist("Artist", 1, false));
        player.getTribe().addHunter(new Hunter("Hunter", 1 , false, false));
        player.getTribe().addShaman(new Shaman("Shaman", 1, false, 3));
        player.getTribe().addCollector(new Collector("Collector", 1, false));
        player.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.BAKER));

        buildingHandler.applyGameEndEffects();
        assertEquals(6, player.getPP());
    }
}
