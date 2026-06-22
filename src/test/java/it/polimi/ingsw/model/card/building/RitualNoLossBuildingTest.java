package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RitualNoLossBuildingTest {
    RitualNoLossBuilding building;
    BuildingHandler handler;
    Player player;

    @BeforeEach
    void setUp() {
        building = new RitualNoLossBuilding(1 , false, 0, 0);
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());
        handler = new BuildingHandler();

        building.register(player, handler);
    }

    @Test
    void ritualNoLossModeActive(){
        assertTrue(player.getNoLossRitualMod());
    }

}