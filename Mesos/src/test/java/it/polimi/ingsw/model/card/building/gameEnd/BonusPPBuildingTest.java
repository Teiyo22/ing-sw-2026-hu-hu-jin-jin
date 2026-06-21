package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.FullSetBuilding;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BonusPPBuildingTest {
    private BuildingHandler buildingHandler;
    private BonusPPBuilding building;
    private Player player;


    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        building = new BonusPPBuilding(1 , false, 0, 0, 25);
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());

        building.register(player, buildingHandler);
    }


    @Test
    void testApplyEffect(){
        assertEquals(0, player.getPP());

        buildingHandler.applyGameEndEffects();
        assertEquals(25, player.getPP());
    }

}