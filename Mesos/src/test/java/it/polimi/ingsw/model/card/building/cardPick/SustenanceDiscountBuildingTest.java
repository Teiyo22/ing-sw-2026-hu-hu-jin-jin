package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.SustenanceDiscountBuilding;
import it.polimi.ingsw.model.card.character.*;
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

    @Test
    void testDoForCollector(){
        building = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                0, 1, 0);
        building.register(player, buildingHandler);

        for(int i = 0; i < 3; i++) {
            Collector collector = new Collector( 1, false);
            Builder builder = new Builder(1, false, 0, 0);
            collector.onPick(player, buildingHandler);
            builder.onPick(player, buildingHandler);
        }

        //collector adds 3 Sustenance Discount
        assertEquals(12, player.getTribe().getSustenanceDiscount());
    }

    @Test
    void testDoForInventor(){
        building = new SustenanceDiscountBuilding(1, false, 3, 3, 1, 0, 0,
                0, 0, 0);

        building.register(player, buildingHandler);

        for(int i = 0; i < 10; i++) {
            Inventor inventor = new Inventor(1, false, InventorType.BOATWRIGHT);
            Artist artist = new Artist(1, false);

            inventor.onPick(player, buildingHandler);
            artist.onPick(player, buildingHandler);

        }

        assertEquals(10, player.getTribe().getSustenanceDiscount());
    }


    @Test
    void testForMultiple(){
        SustenanceDiscountBuilding building1 = new SustenanceDiscountBuilding(1, false, 3, 3, 1, 0, 0,
                0, 0, 0);
        SustenanceDiscountBuilding building2 = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                1, 0, 0);

        building1.register(player, buildingHandler);
        building2.register(player, buildingHandler);

        for(int i = 0; i < 3; i++){
            Inventor inventor = new Inventor(1, false, InventorType.BOATWRIGHT);
            Collector collector = new Collector(1, false);
            inventor.onPick(player, buildingHandler);
            collector.onPick(player, buildingHandler);

        }

        for(int i = 0; i < 2; i++){
            Artist artist = new Artist(1, false);
            artist.onPick(player, buildingHandler);

        }

        assertEquals(14, player.getTribe().getSustenanceDiscount(), "Collectors add 9 (due to the base card bonus), Inventors 3, Artists 2");
    }

}
