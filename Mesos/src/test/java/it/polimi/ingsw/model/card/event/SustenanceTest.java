package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.card.character.Collector;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SustenanceTest {
    List<Player> players;
    Player player1;
    Player player2;
    Player player3;
    int ppMultiplier = -10;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        player1 = new Player("Enrico", Totem.BLACK);
        player2 = new Player("Marco", Totem.YELLOW);
        player3 = new Player("Franco", Totem.RED);
    }

    @Test
    void testSustenanceEventEffect(){
        players.add(player1);
        players.add(player2);
        players.add(player3);

        for(int i=0; i<10; i++) {
            player1.getTribe().addCollector();
            player1.getTribe().addArtist();
            player1.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.BOATWRIGHT));
            player1.getTribe().addInventor(new Inventor("Inventor", 1, false, InventorType.FISHERMAN));

            player2.getTribe().addHunter();
            player2.getTribe().addArtist();

            player3.getTribe().addHunter();
            player3.getTribe().addCollector();
        }
        Collector collector = new Collector("Collector", 1, false);
        collector.addToTribeOf(player3);

        player1.setFood(0);
        player2.setFood(100);
        player3.setFood(30);

        for(Player player: players) {  //apply the effects for each player
            //get the number of tribe members
            int foodCost = Math.max(player.getTribe().getTribeSize() - player.getTribe().getSustenanceDiscount(), 0);
            int unfedCount =  foodCost - player.getFood();

            if (unfedCount > 0) {
                player.setFood(0);  //spend all the food
                player.addPP(unfedCount * ppMultiplier);

            } else {
                player.addFood(-foodCost);  //otherwise just remove the needed amount of food, 1 per member
            }
        }

        assertEquals(-400, player1.getPP());
        assertEquals(0, player1.getFood());

        assertEquals(0, player2.getPP());
        assertEquals(80, player2.getFood());

        assertEquals(0, player3.getPP());
        assertEquals(12, player3.getFood());

    }

}