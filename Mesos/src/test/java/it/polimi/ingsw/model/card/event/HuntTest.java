package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.character.Hunter;
import it.polimi.ingsw.model.player.Player;


import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HuntTest {
    private int ppMultiplier = 10;
    Player player1;
    Player player2;
    List<Player> players;
    Game game;

    Hunt hunt;


    @BeforeEach
    void setUp() {
        player1 = new Player("Enrico", Totem.BLACK);

        player2 = new Player("Marco", Totem.YELLOW);

        players = new ArrayList<>();

        players.add(player1);
        players.add(player2);

        game = new Game(PlayerConfig.TWO, players);


        player1.setTribe(new Tribe());
        player2.setTribe(new Tribe());
        hunt = new Hunt( 1, false, 10);
    }

    @Test
    void applyMultiplier(){
        Hunter hunter = new Hunter(1, false, false);

        for(int i=0; i<3; i++){
            player1.getTribe().addHunter(hunter);
        }
        for(int i=0; i<10; i++){
            hunter.addToTribeOf(player2);
        }

        hunt.onEvent(game);

        assertEquals(3, player1.getFood());
        assertEquals(10, player2.getFood());

        assertEquals(30, player1.getPP());
        assertEquals(100, player2.getPP());
    }

}