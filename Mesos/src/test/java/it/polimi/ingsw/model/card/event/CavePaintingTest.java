package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
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
    private int bonusPP = 3;
    private int malusPP = 2;
    private int numArtistsMalus = 2;
    private int numArtistsBonus = 3;
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

        game.setLobbyState(new LobbyRunningState(new LobbyController(1, 2)));

        cavePainting = new CavePainting(1, false, bonusPP, malusPP, numArtistsBonus, numArtistsMalus);
    }

    @Test
    void testMalusApplied() {
        Artist artist = new Artist(1, false);
        player1.getTribe().addArtist(artist);

        cavePainting.onEvent(game);

        assertEquals(-malusPP, player1.getPP());
    }


    @Test
    void testBonusApplied(){
        for(int i = 0; i < 10; i++){
            Artist artist = new Artist(1, false);
            player1.getTribe().addArtist(artist);
        }

        cavePainting.onEvent(game);

        assertEquals(bonusPP*player1.getTribe().getArtistCount(), player1.getPP());

    }
}