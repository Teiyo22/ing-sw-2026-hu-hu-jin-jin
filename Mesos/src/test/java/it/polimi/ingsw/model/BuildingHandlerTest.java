package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.card.VisitableCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.gameState.GameEndState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingHandlerTest {
    private BuildingHandler buildingHandler;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp(){
        buildingHandler = new BuildingHandler();
        p1 = new Player("Pippo", Totem.YELLOW);
        p1.initTribe(0);
        p2 = new Player("Gino", Totem.BLUE);
        p2.initTribe(0);
    }

    //Test for NewFullSetBuilding
    @Test
    void applyCardPickNotNewFullSetEffects() {
        NewFullSetBuilding newFullSetBuilding = new NewFullSetBuilding(1,false,0,2);
        newFullSetBuilding.onPick(p1,buildingHandler);

        buildingHandler.applyCardPickEffects(null, p1);
        assertEquals(0, p1.getFood());
    }

    @Test
    void applyCardPickNewFullSetEffects() {
        NewFullSetBuilding newFullSetBuilding = new NewFullSetBuilding(1,false,0,2);
        newFullSetBuilding.onPick(p1,buildingHandler);

        Shaman shaman = new Shaman( 1, false, 2);
        Builder builder = new Builder( 1, false, 1,2);
        Inventor inventor = new Inventor( 1, false, InventorType.BAKER);
        Artist artist = new Artist(1, false);
        Collector collector = new Collector( 1, false);
        Hunter hunter = new Hunter(1, false, false);

        p1.getTribe().addBuilder(builder);
        buildingHandler.applyCardPickEffects(builder, p1);
        p1.getTribe().addInventor(inventor);
        buildingHandler.applyCardPickEffects(inventor, p1);
        p1.getTribe().addArtist(artist);
        buildingHandler.applyCardPickEffects(artist, p1);
        p1.getTribe().addCollector(collector);
        buildingHandler.applyCardPickEffects(collector, p1);
        p1.getTribe().addShaman(shaman);
        buildingHandler.applyCardPickEffects(shaman, p1);
        p1.getTribe().addHunter(hunter);
        buildingHandler.applyCardPickEffects(hunter, p1);

        assertEquals(5, p1.getFood());
    }


    //Test for InventorPairBuilding
    @Test
    void applyCardPickEffectsInventorPairBuildingTest() {
        InventorPairBuilding inventorPairBuilding = new InventorPairBuilding(1, false, 0, 3);
        inventorPairBuilding.onPick(p1, buildingHandler);

        Inventor inventor = new Inventor(1, false, InventorType.BAKER);

        p1.getTribe().addInventor(inventor);
        buildingHandler.applyCardPickEffects(inventor, p1);
        assertEquals(0,p1.getFood());

        p1.getTribe().addInventor(inventor);
        buildingHandler.applyCardPickEffects(inventor, p1);
        assertEquals(3, p1.getFood());

        p1.getTribe().addInventor(inventor);
        buildingHandler.applyCardPickEffects(inventor, p1);
        assertEquals(3, p1.getFood());
    }


    @Test
    void differentTypesTest() {
        InventorPairBuilding inventorPairBuilding = new InventorPairBuilding(1, false, 0, 3);
        inventorPairBuilding.onPick(p1, buildingHandler);

        Inventor inventor1 = new Inventor(1, false, InventorType.BAKER);
        Inventor inventor = new Inventor(1,false, InventorType.BOATWRIGHT);

        p1.getTribe().addInventor(inventor1);
        buildingHandler.applyCardPickEffects(inventor1, p1);
        p1.getTribe().addInventor(inventor);
        buildingHandler.applyCardPickEffects(inventor, p1);

        assertEquals(0, p1.getFood());
    }

    //Test for SustenanceDiscountBuilding
    @Test
    void applyCardPickEffectsSustenanceDiscountBuildingTest() {
        SustenanceDiscountBuilding sustenanceBuilding = new SustenanceDiscountBuilding( 1, false, 0, 3,
                2, 0, 0, 0, 0, 0);
        sustenanceBuilding.onPick(p1, buildingHandler);

        Inventor inventor = new Inventor(1, false, InventorType.BAKER);
        p1.getTribe().addInventor(inventor);
        buildingHandler.applySustenanceDiscountEffects();
        assertEquals(2, p1.getTribe().getSustenanceDiscount());

        p1.getTribe().addInventor(inventor);
        buildingHandler.applySustenanceDiscountEffects();
        assertEquals(4, p1.getTribe().getSustenanceDiscount());

    }


    @Test
    void testDoForCollector(){
        SustenanceDiscountBuilding building = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                0, 1, 0);
        building.register(p1, buildingHandler);

        for(int i = 0; i < 3; i++) {
            Collector collector = new Collector( 1, false);
            Builder builder = new Builder(1, false, 0, 0);
            p1.getTribe().addCollector(collector);
            p1.getTribe().addBuilder(builder);
        }

        buildingHandler.applySustenanceDiscountEffects();

        //collectors add 9 additional discount values
        assertEquals(12, p1.getTribe().getSustenanceDiscount());
    }

    @Test
    void testForMultiple(){
        SustenanceDiscountBuilding building1 = new SustenanceDiscountBuilding(1, false, 3, 3, 1, 0, 0,
                0, 0, 0);
        SustenanceDiscountBuilding building2 = new SustenanceDiscountBuilding(1, false, 3, 3, 0, 0, 0,
                1, 0, 0);

        building1.register(p1, buildingHandler);
        building2.register(p1, buildingHandler);

        for(int i = 0; i < 3; i++){
            Inventor inventor = new Inventor(1, false, InventorType.BOATWRIGHT);
            Collector collector = new Collector(1, false);
            p1.getTribe().addInventor(inventor);
            p1.getTribe().addCollector(collector);

        }

        for(int i = 0; i < 2; i++){
            Artist artist = new Artist(1, false);
            p1.getTribe().addArtist(artist);

        }
        //adds 5
        buildingHandler.applySustenanceDiscountEffects();

        //collectors add 9 disocunt points
        assertEquals(14, p1.getTribe().getSustenanceDiscount());
    }

    //Test for HuntBuilding
    @Test
    void applyHuntEffectsTest() {
        HuntBuilding huntBuilding = new HuntBuilding(1, false, 0, 0, 1, 1);
        huntBuilding.onPick(p1, buildingHandler);
        Hunter hunter = new Hunter(1, false, false);
        p1.getTribe().addHunter(hunter);
        p1.getTribe().addHunter(hunter);

        buildingHandler.applyHuntEffects();

        assertEquals( 2 , p1.getFood());
        assertEquals(2 , p1.getPP());
    }

    @Test
    void applyHuntEffectsNoHuntersTest() {
        HuntBuilding huntBuilding = new HuntBuilding(1, false, 0, 0, 1, 1);
        huntBuilding.onPick(p1, buildingHandler);

        buildingHandler.applyHuntEffects();

        assertEquals(0, p1.getFood());
        assertEquals(0, p1.getPP());
    }


    //Test for CavePaintingBuilding
    @Test
    void applyCavePaintingEffectsTest() {
        CavePaintingBuilding cavePaintingBuilding = new CavePaintingBuilding(1, false, 0, 0, 1);
        cavePaintingBuilding.onPick(p1, buildingHandler);

        Artist artist = new Artist(1, false);

        p1.getTribe().addArtist(artist);
        p1.getTribe().addArtist(artist);

        buildingHandler.applyCavePaintingEffects();

        assertEquals(2, p1.getFood());
    }

    @Test
    void applyCavePaintingEffectsNoArtistsTest() {
        CavePaintingBuilding cavePaintingBuilding = new CavePaintingBuilding(1, false, 0, 0, 1);
        cavePaintingBuilding.onPick(p1, buildingHandler);

        buildingHandler.applyCavePaintingEffects();

        assertEquals(0, p1.getFood());
    }


    //Test for OrderTileBuilding
    @Test
    void applyNegativeOrderTileEffectsTest() {
        OrderTileBuilding orderTileBuilding = new OrderTileBuilding(1, false, 0, 0);
        orderTileBuilding.onPick(p1, buildingHandler);

        OrderSlot o = new OrderSlot(-1);
        o.setPlayer(p1);

        buildingHandler.applyOrderTileEffects(o);

        assertEquals(0, p1.getFood());
    }

    @Test
    void applyPositiveOrderTileEffectsTest() {
        OrderTileBuilding orderTileBuilding = new OrderTileBuilding(1, false, 0, 0);
        orderTileBuilding.onPick(p1, buildingHandler);

        OrderSlot o = new OrderSlot(3);
        o.setPlayer(p1);

        buildingHandler.applyOrderTileEffects(o);

        assertEquals(1, p1.getFood());
    }


    //Test for ExtraActionBuilding
    @Test
    void applyExtraActionEffectsTest() {
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Game game = new Game(PlayerConfig.TWO, players);

        ExtraActionBuilding extraActionBuilding = new ExtraActionBuilding(1, false, 0, 3);
        extraActionBuilding.onPick(players.get(0), buildingHandler);

        ExtraActionState extraActionState = new ExtraActionState(game, buildingHandler);

        buildingHandler.applyExtraActionEffects(extraActionState, 0);

        assertEquals(players.get(0), extraActionState.getCurrPlayer());
    }


    //Test for BonusPPBuilding
    @Test
    void applyGameEndEffectsBonusPPTest() {
        BonusPPBuilding bonusPPBuilding = new BonusPPBuilding(1, false, 0, 0, 25);
        bonusPPBuilding.onPick(p1, buildingHandler);

        buildingHandler.applyGameEndEffects();

        assertEquals(25, p1.getPP());
    }


    //Test for BuilderBonusBuilding
    @Test
    void applyGameEndEffectsBuilderBonusTest() {
        BuilderDoublePPBuilding builderBonusBuilding = new BuilderDoublePPBuilding(1, false,0 ,0);
        Builder builder = new Builder(1, false, 1,0);
        p1.getTribe().addBuilder(builder);
        builderBonusBuilding.onPick(p1, buildingHandler);

        int initialBonus = p1.getTribe().getBuilderBonusPP();

        p1.addPP(initialBonus);

        buildingHandler.applyGameEndEffects();
        assertEquals(initialBonus*2, p1.getTribe().getPP());
    }


    //Test for FullSetBuilding
    @Test
    void applyGameEndEffectsFullSetTest() {
        FullSetBuilding fullSetBuilding = new FullSetBuilding(1, false, 0, 0);
        fullSetBuilding.onPick(p1, buildingHandler);

        buildingHandler.applyGameEndEffects();
        assertEquals(0, p1.getPP());

        p1.getTribe().addBuilder(new Builder(1, false, 0, 0));
        p1.getTribe().addArtist(new Artist(1, false));
        p1.getTribe().addHunter(new Hunter(1 , false, false));
        p1.getTribe().addShaman(new Shaman(1, false, 3));
        p1.getTribe().addCollector(new Collector(1, false));

        buildingHandler.applyGameEndEffects();
        assertEquals(0, p1.getPP());

        p1.getTribe().addInventor(new Inventor(1, false, InventorType.BAKER));

        buildingHandler.applyGameEndEffects();
        assertEquals(6, p1.getPP());
    }


    //Test for CharacterBonusBuilding
    @Test
    void applyGameEndEffectsCharacterBonusTest() {
        CharacterBonusBuilding characterBonusBuilding = new CharacterBonusBuilding(1,false, 0,0
                ,0,0,0,0,4,0);
        characterBonusBuilding.onPick(p1, buildingHandler);

        p1.getTribe().addArtist(new Artist(1, false));

        buildingHandler.applyGameEndEffects();
        assertEquals(4*p1.getTribe().getArtistCount(), p1.getPP());

        p1.setPP(0);

        p1.getTribe().addArtist(new Artist(1, false));
        p1.getTribe().addArtist(new Artist(1, false));
        p1.getTribe().addBuilder(new Builder(1, false, 0, 0));

        buildingHandler.applyGameEndEffects();
        assertEquals(4*p1.getTribe().getArtistCount(), p1.getPP());

    }
}