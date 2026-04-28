package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.board.OrderSlot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTileBuildingTest {
    private BuildingHandler buildingHandler;
    private Player player;
    private OrderTileBuilding building;

    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        player = new Player("X", Totem.BLACK);
        building = new OrderTileBuilding("OrderTileBuilding", 1, false, 3, 3, buildingHandler);
        building.onPick(player, buildingHandler);
    }

    @Test
    void testApplyEffect(){
        OrderSlot o = new OrderSlot(0);
        o.setPlayer(player);
        building.applyEffect(o);
        assertEquals(0, player.getFood());

        o = new OrderSlot(3);
        building.applyEffect(o);
        assertEquals(0, player.getFood());

        o.setPlayer(player);
        building.applyEffect(o);
        assertEquals(3, player.getFood());
    }
}