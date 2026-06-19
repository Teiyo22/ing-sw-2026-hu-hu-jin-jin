package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuilderTest {
    private Builder builder;
    private Player player;

    @BeforeEach
    void setUp() {
        builder = new Builder("Builder", 1, false, 2, 2);
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());
    }

    @Test
    void testAddToTribeOf() {
        builder.addToTribeOf(player);
        assertEquals(1, player.getTribe().getBuilderCount());
    }

}
