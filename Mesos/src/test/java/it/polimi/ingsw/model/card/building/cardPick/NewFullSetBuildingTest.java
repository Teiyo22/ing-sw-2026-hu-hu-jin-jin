package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.NewFullSetBuilding;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import it.polimi.ingsw.model.player.Tribe;
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
        player.setTribe(new Tribe());

        building = new NewFullSetBuilding(1, false, 0, 0);

        building.register(player, buildingHandler);
    }

    @Test
    void testCheckAndAdd(){
        player.getTribe().addInventor(new Inventor(1, false, InventorType.BAKER));
        player.getTribe().addArtist(new Artist( 1, false));
        player.getTribe().addBuilder(new Builder( 1, false, 2, 1));
        player.getTribe().addCollector(new Collector( 1, false));

        Hunter hunter = new Hunter(1, false, false);
        player.getTribe().addHunter(hunter);
        hunter.onPick(player, buildingHandler);

        assertEquals(0, player.getFood());

        Shaman shaman = new Shaman(1, false, 3);
        player.getTribe().addShaman(shaman);
        shaman.onPick(player, buildingHandler);

        assertEquals(5, player.getFood());
    }
}
