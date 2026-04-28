package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.FullSetBuilding;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Totem;

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
        building = new FullSetBuilding("FullSetBuilding",1 , false, 3, 3, buildingHandler);
        player = new Player("X", Totem.BLACK);
    }

    @Test
    void testApplyEffect(){
        building.onPick(player, buildingHandler);
        building.applyEffect();
        assertEquals(0, player.getPP());

        player.getTribe().addBuilder(new Builder("Builder", 1, false, 2, 2));
        player.getTribe().addArtist();
        player.getTribe().addHunter();
        player.getTribe().addShaman(new Shaman("Shaman", 1, false, 3));
        player.getTribe().addCollector();
        player.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.BAKER));
        building.applyEffect();
        assertEquals(6, player.getPP());
    }
}
