package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.CharacterBonusBuilding;
import it.polimi.ingsw.model.card.character.Artist;
import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.gameState.GameEndState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterBonusBuildingTest {
    private BuildingHandler buildingHandler;
    private CharacterBonusBuilding building;
    private Player player;


    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        building = new CharacterBonusBuilding(1 , false, 0, 0, 0, 0, 0, 0, 4, 0);
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());

        building.register(player, buildingHandler);
    }


    @Test
    void testApplyEffect(){
        player.getTribe().addArtist(new Artist(1, false));

        buildingHandler.applyGameEndEffects();
        assertEquals(4*player.getTribe().getArtistCount(), player.getPP());

        player.setPP(0);

        player.getTribe().addArtist(new Artist(1, false));
        player.getTribe().addArtist(new Artist(1, false));
        player.getTribe().addBuilder(new Builder(1, false, 0, 0));

        buildingHandler.applyGameEndEffects();
        assertEquals(4*player.getTribe().getArtistCount(), player.getPP());

    }







}