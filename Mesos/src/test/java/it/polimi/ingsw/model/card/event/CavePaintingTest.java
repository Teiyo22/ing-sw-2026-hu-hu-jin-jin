package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavePaintingTest {
    Player player1;
    private int bonusPP = 5;
    private int malusPP = 10;
    private int numArtistsMalus = 2;
    private int numArtistsBonus = 10;

    @BeforeEach
    void setUp(){
        player1 = new Player("Enrico", Totem.BLACK);
    }

    @Test
    void testMalusApplied() {
        Player player1 = new Player("Enrico", Totem.BLACK);
        player1.getTribe().addArtist();
        int initialPP = player1.getPP();
        int numArtists = player1.getTribe().getArtistCount();

            if(numArtists <= numArtistsMalus){
                player1.addPP(-malusPP);
                assertEquals(initialPP - 10, player1.getPP(), "Player should lose 10 PP from malus logic -10");

            } else if (numArtists >= numArtistsBonus){
                player1.addPP(bonusPP * numArtists);
                assertEquals(initialPP +2, player1.getPP(), "Player should gain 2 PP from bonus logic +2");
            }
    }
}