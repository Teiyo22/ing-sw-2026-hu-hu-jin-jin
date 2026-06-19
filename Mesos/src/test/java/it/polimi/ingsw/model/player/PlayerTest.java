package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.CavePaintingBuilding;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.card.building.AbstractBuilding;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Game g;
    private List<Player> players;
    private Player p;
    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeEach
    public void setUp(){
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));
        players.add(new Player("Carlo", Totem.WHITE));
        g = new Game(PlayerConfig.FIVE, players);
        p = players.get(0);
        p1 = players.get(1);
        p2 = players.get(2);
        p3 = players.get(3);
    }


    @Test
    void addPPTest(){
        p.addPP(6);
        assertEquals(6,p.getPP());
    }

    @Test
    void addFoodTest(){
        int initialFood = p.getFood();
        p.addFood(7);
        assertEquals(initialFood + 7, p.getFood());
    }

    @Test
    void enableNoLossRitualMod(){
        p.enableNoLossRitualMod();
        assertTrue(p.getNoLossRitualMod());
    }

    @Test
    void addBuildingTest(){
        AbstractBuilding b= new CavePaintingBuilding(1,false,5,2,2);
        p.addBuilding(b);
        assertEquals(1,p.getBuildings().size());

    }

    @Test
    void enableDoubleRitualModTest(){
        p.enableDoubleRitualMod();
        assertTrue(p.getDoubleRitualMod());
    }

    @Test
    void getTribeTest(){
        assertNotNull(p.getTribe());
    }

    @Test
    void setRankTest(){
        p.setRank(67);
        assertEquals(67,p.getRank());
    }

    @Test
    void compareToTest(){
        p.setPP(10);
        p1.setPP(10);
        p2.setPP(10);
        p3.setPP(9);
        p.setFood(10);
        p1.setFood(10);
        p2.setFood(9);
        p3.setFood(100);

        assertEquals(0, p.compareTo(p1));
        assertEquals(-1, p.compareTo(p2));
        assertEquals(-1 , p.compareTo(p3));
        assertEquals(1 , p2.compareTo(p));
    }
}
