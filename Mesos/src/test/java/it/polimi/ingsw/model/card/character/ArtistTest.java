package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistTest {
    private Artist artist;
    private Player player;

    @BeforeEach
    void setUp() {
        artist = new Artist("Artist", 1, false);
        player = new Player("X", Totem.BLACK);
    }

    @Test
    void testAddToTribeOf() {
        artist.addToTribeOf(player);
        assertEquals(1, player.getTribe().getArtistCount());
    }

    @Test
    void testClone(){
        AbstractCard artist2 = artist.clone();
        assertEquals(artist2.getID(), artist.getID());
        assertEquals(artist2.getType(), artist.getType());
        assertEquals(artist2.isFinal(), artist.isFinal());
    }

}
