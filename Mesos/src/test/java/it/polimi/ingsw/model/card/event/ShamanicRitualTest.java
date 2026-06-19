package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShamanicRitualTest {
    private int bonusPP = 10;
    private int malusPP = 100;
    List<Player> players;
    Player player1;
    Player player2;
    Player player3;
    Game game;
    ShamanicRitual shamanicRitualEvent;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        player1 = new Player("Enrico", Totem.BLACK);
        player2 = new Player("Marco", Totem.YELLOW);
        player3 = new Player("Franco", Totem.RED);


        players.add(player1);
        players.add(player2);
        players.add(player3);

        game = new Game(PlayerConfig.THREE, players);

        game.setLobbyState(new LobbyRunningState(new LobbyController(1, 3)));

        player1.setTribe(new Tribe());
        player2.setTribe(new Tribe());
        player3.setTribe(new Tribe());

        shamanicRitualEvent = new ShamanicRitual(1, false, bonusPP, malusPP);

    }

    @Test
    void testMinMaxBonusAllEqual() {
        player1.getTribe().addStars(10);
        player2.getTribe().addStars(10);
        player3.getTribe().addStars(10);


        shamanicRitualEvent.onEvent(game);


        assertEquals( bonusPP-malusPP, players.get(0).getPP());
        assertEquals( bonusPP-malusPP, players.get(1).getPP());
        assertEquals( bonusPP-malusPP, players.get(2).getPP());
    }


    @Test
    void testMinMaxBonus() {
        player1.getTribe().addStars(10);
        player2.getTribe().addStars(100);
        player3.getTribe().addStars(1);


        shamanicRitualEvent.onEvent(game);

        assertEquals( 0, player1.getPP(), "Il player 1 non ottiene nulla");
        assertEquals( bonusPP, player2.getPP(), "player 2 ha il numero di stelle massimo, prende il bonus");
        assertEquals( -malusPP, player3.getPP(), "malus");
    }


    @Test
    void NolossModeTest(){
        player1.getTribe().addStars(1);
        player2.getTribe().addStars(100);

        player1.addPP(100);

        player1.getTribe().setNoLossRitualMod(true);

        shamanicRitualEvent.onEvent(game);

        assertEquals(100, player1.getPP());
    }


    @Test
    void DoubleModeTest(){
        player1.getTribe().addStars(1);
        player2.getTribe().addStars(100);

        player2.getTribe().setDoubleRitualMod(true);

        shamanicRitualEvent.onEvent(game);

        assertEquals(2*bonusPP, player2.getPP());
    }
}