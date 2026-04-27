package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewFullSetBuildingTest {
    private BuildingHandler buildingHandler;
    private Player player;
    private NewFullSetBuilding building;

    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        player = new Player("X", Totem.BLACK);
        building = new NewFullSetBuilding("NewFullSetBuilding", 1, false, 3, 3, buildingHandler);
    }

    @Test
    void testCheckAndAdd(){
        player.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.BAKER));
        player.getTribe().addArtist();
        player.getTribe().addBuilder(new Builder("Builder", 1, false, 2, 1));
        player.getTribe().addCollector();

        building.onPick(player, buildingHandler);

        player.getTribe().addHunter();
        building.checkAndAdd();
        assertEquals(0, player.getFood());

        player.getTribe().addShaman(new Shaman("Shaman", 1, false, 3));
        building.checkAndAdd();
        assertEquals(5, player.getFood());
    }
}
