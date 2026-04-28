package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SustenanceDiscountBuildingTest {
    private SustenanceDiscountBuilding building;
    private BuildingHandler buildingHandler;
    private Player player;

    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        player = new Player ("X", Totem.BLACK);
    }

    @Test
    void testOnPickEmptyTribe(){
        building = new SustenanceDiscountBuilding("SustenanceDiscountBuilding", 1, false, 3, 3,
                buildingHandler, 1, 1, 1,
                1, 1, 1);
        building.onPick(player, buildingHandler);
        assertEquals(0, player.getTribe().getSustenanceDiscount());
    }

    @Test
    void testOnPickGeneralCase(){
        building = new SustenanceDiscountBuilding("SustenanceDiscountBuilding", 1, false, 3, 3,
                buildingHandler, 2, 0, 0,
                0, 0, 0);
        player.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.BAKER));
        player.getTribe().addArtist();
        building.onPick(player, buildingHandler);
        assertEquals(2, player.getTribe().getSustenanceDiscount());
    }

    @Test
    void testDoForInventor(){
        building = new SustenanceDiscountBuilding("SustenanceDiscountBuilding", 1, false, 3, 3,
                buildingHandler, 2, 0, 0,
                0, 0, 0);
        building.onPick(player, buildingHandler);
        building.visit(new Inventor("Inventor", 1, false, InventorType.BAKER));
        assertEquals(2, player.getTribe().getSustenanceDiscount());
    }

}
