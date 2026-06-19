package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.character.Artist;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CavePaintingTest {
    Player player1;
    Player player2;
    List<Player> players;
    private int bonusPP = 5;
    private int malusPP = 10;
    private int numArtistsMalus = 2;
    private int numArtistsBonus = 10;
    Game game;

    CavePainting cavePainting;

    @BeforeEach
    void setUp(){
        players = new ArrayList<>();


        player1 = new Player("Enrico", Totem.BLACK);
        player1.setTribe(new Tribe());
        players.add(player1);

        player2 = new Player("Marco", Totem.RED);
        player2.setTribe(new Tribe());
        players.add(player2);



        game = new Game(PlayerConfig.TWO, players);

        cavePainting = new CavePainting("CavePainting", 1, false, bonusPP, malusPP, numArtistsBonus, numArtistsMalus);
    }

    @Test
    void testMalusApplied() {
        Artist artist = new Artist("Artist", 1, false);
        player1.getTribe().addArtist(artist);

        int initialPP = player1.getPP();
        int numArtists = player1.getTribe().getArtistCount();

        cavePainting.onEvent(game);

        if(numArtists <= numArtistsMalus){
            assertEquals(initialPP - malusPP, player1.getPP(), "Player should lose 10 PP from malus logic: -3");
        } else if (numArtists >= numArtistsBonus){
            assertEquals(initialPP + bonusPP, player1.getPP(), "Player should gain 2 PP from bonus logic +5");
        } else{
            assertEquals(initialPP, player1.getPP());
        }
    }
}