package it.polimi.ingsw.model.player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.model.card.character.*;



public class TribeTest {
    private Tribe t;

    @BeforeEach
    void setUp() {
        t = new Tribe();
    }


    @Test
    void addSustenanceDiscountTest() {
        t.addSustenanceDiscount(2);
        assertEquals(2, t.getSustenanceDiscount());
    }

    @Test
    void addStarsTest() {
        t.addStars(2);
        assertEquals(2, t.getStars());
    }

    @Test
    void addInventorTest() {
        Inventor i = new Inventor("Inventor", 1, false, InventorType.FLETCHER);
        t.addInventor(i);
        assertEquals(1, t.getInventorCount());
    }

    @Test
    void addCollectorTest() {
        t.addCollector();
        assertEquals(1, t.getCollectorCount());
    }

    @Test
    void addShamanTest() {
        Shaman s = new Shaman("Shaman", 1, false, 2);
        t.addShaman(s);
        assertEquals(1, t.getShamanCount());
    }

    @Test
    void addBuilderTest() {
        Builder b = new Builder("Builder", 1, false, 5, 2);
        t.addBuilder(b);
        assertEquals(1, t.getBuilderCount());
    }

    @Test
    void addArtistTest() {
        t.addArtist();
        assertEquals(1, t.getArtistCount());
    }

    @Test
    void addHunterTest() {
        t.addHunter();
        assertEquals(1, t.getHunterCount());
    }
}
