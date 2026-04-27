package it.polimi.ingsw.model.card.character;
import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShamanTest {
    private Player player;
    private Shaman shaman;

    @BeforeEach
    void setUp() {
        shaman = new Shaman("Shaman", 1, false, 3);
        player = new Player("X", Totem.BLACK);
    }

    @Test
    void testAddToTribeOf() {
        shaman.addToTribeOf(player);
        assertEquals(1, player.getTribe().getShamanCount());
        assertEquals(3, player.getTribe().getStars());
    }
}
