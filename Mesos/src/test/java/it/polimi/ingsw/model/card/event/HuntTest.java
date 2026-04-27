package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;


import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HuntTest {
    AbstractEvent hunt;
    private int ppMultiplier = 10;
    Player player1;
    Player player2;
    List<Player> players;

    @BeforeEach
    void setUp() {
        player1 = new Player("Enrico", Totem.BLACK);
        player2 = new Player("Marco", Totem.YELLOW);
        players = new ArrayList<>();
    }

    @Test
    void applyMultiplier(){

        for(int i=0; i<3; i++){
            player1.getTribe().addHunter();
        }
        for(int i=0; i<10; i++){
            player2.getTribe().addHunter();
        }

        players.add(player1);
        players.add(player2);


        for(Player player: players){  //apply effects for each player
            int numHunters = player.getTribe().getHunterCount();
            player.addFood(numHunters);
            player.addPP(numHunters * ppMultiplier);
        }

        assertEquals(3, player1.getFood());
        assertEquals(10, player2.getFood());

        assertEquals(30, player1.getPP());
        assertEquals(100, player2.getPP());
    }

}