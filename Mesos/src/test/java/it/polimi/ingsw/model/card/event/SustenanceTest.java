package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SustenanceTest {
    private List<Player> players;
    private Player player1;
    private Player player2;
    private Player player3;

    private final int PP_MULTIPLIER = 10;

    private Game game;
    private Sustenance sustenanceEvent;

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

        player1.setTribe(new Tribe());
        player2.setTribe(new Tribe());
        player3.setTribe(new Tribe());

        sustenanceEvent = new Sustenance(1, false, PP_MULTIPLIER);
    }

    @Test
    void testSustenanceEventEffect() {
        for (int i = 0; i < 10; i++) {
            Collector collector = new Collector(1, true);
            Artist artist = new Artist(1, false);
            Hunter hunter = new Hunter(1, false, false);

            player1.getTribe().addCollector(collector);
            player1.getTribe().addArtist(artist);
            player1.getTribe().addInventor(new Inventor(1, false, InventorType.BOATWRIGHT));
            player1.getTribe().addInventor(new Inventor(1, false, InventorType.FISHERMAN));

            player2.getTribe().addHunter(hunter);
            player2.getTribe().addArtist(artist);

            player3.getTribe().addHunter(hunter);
            player3.getTribe().addCollector(collector);
        }

        player3.getTribe().addCollector(new Collector( 1, true));

        player1.setFood(0);
        player2.setFood(100);
        player3.setFood(30);


        sustenanceEvent.onEvent(game);

        assertEquals(-400, player1.getPP());
        assertEquals(0, player1.getFood());

        assertEquals(0, player2.getPP());
        assertEquals(80, player2.getFood());

        assertEquals(0, player3.getPP());
        assertEquals(9, player3.getFood());
    }
}