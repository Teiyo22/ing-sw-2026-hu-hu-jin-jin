package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.character.Artist;
import it.polimi.ingsw.model.card.character.Collector;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import it.polimi.ingsw.model.player.Tribe;
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
        player.setTribe(new Tribe());
    }
/*
    @Test
    void testOnPickEmptyTribe(){
        building = new SustenanceDiscountBuilding("SustenanceDiscountBuilding", 1, false, 3, 3,0, 0, 0,
                1, 0, 0);
        building.onPick(player, buildingHandler);

        buildingHandler.applyCardPickEffects();
        assertEquals(0, player.getTribe().getSustenanceDiscount());
    }
*/
    @Test
    void testForCollector(){
        building = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                0, 1, 0);
        building.register(player, buildingHandler);

        for(int i = 0; i < 3; i++) {
            Collector collector = new Collector( 1, false);
            player.getTribe().addCollector(collector);
            player.getTribe().addArtist(new Artist(1, false));
            buildingHandler.applyCardPickEffects(collector, player);
        }

        assertEquals(3, player.getTribe().getSustenanceDiscount());
    }

    @Test
    void testDoForInventor(){
        building = new SustenanceDiscountBuilding(1, false, 3, 3, 1, 0, 0,
                0, 0, 0);

        building.register(player, buildingHandler);

        for(int i = 0; i < 10; i++) {
            Inventor inventor = new Inventor(1, false, InventorType.BOATWRIGHT);
            player.getTribe().addInventor(inventor);
            player.getTribe().addArtist(new Artist(1, false));

            buildingHandler.applyCardPickEffects(inventor, player);

        }

        assertEquals(10, player.getTribe().getSustenanceDiscount());
    }


    @Test
    void testForMultiple(){
        SustenanceDiscountBuilding building1 = new SustenanceDiscountBuilding(1, false, 3, 3, 1, 0, 0,
                0, 0, 0);
        SustenanceDiscountBuilding building2 = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                0, 1, 0);

        building1.register(player, buildingHandler);
        building2.register(player, buildingHandler);

        for(int i = 0; i < 3; i++){
            Inventor inventor = new Inventor(1, false, InventorType.BOATWRIGHT);
            player.getTribe().addInventor(inventor);
            player.getTribe().addArtist(new Artist(1, false));

            buildingHandler.applyCardPickEffects(inventor, player);
        }

        for(int i = 0; i < 2; i++){
            Collector collector = new Collector(1, false);
            player.getTribe().addCollector(collector);

            buildingHandler.applyCardPickEffects(collector, player);
        }

        assertEquals(5, player.getTribe().getSustenanceDiscount());
    }

}
