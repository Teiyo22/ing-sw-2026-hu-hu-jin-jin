package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShamanicRitualTest {
    private int bonusPP = 10;
    private int malusPP = 100;
    List<Player> players;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        player1 = new Player("Enrico", Totem.BLACK);
        player2 = new Player("Marco", Totem.YELLOW);
        player3 = new Player("Franco", Totem.RED);

    }

    @Test
    void testMinMaxBonusAllEqual() {

        players.add(player1);
        players.add(player2);
        players.add(player3);

        player1.getTribe().addStars(10);
        player2.getTribe().addStars(10);
        player3.getTribe().addStars(10);


        int minStars = players.getFirst().getTribe().getStars();
        int maxStars = minStars;  //number of stars owned by the player(s) who has the most

        for (int i = 1; i < players.size(); i++) {
            int stars = players.get(i).getTribe().getStars();

            if (stars < minStars)
                minStars = stars;
            else if (stars > maxStars)
                maxStars = stars;
        }

        //apply effects to players, depending on their number of stars
        for (Player player : players) {
            int playerStars = player.getTribe().getStars();

            if (playerStars == minStars) {
                if (!player.getNoLossRitualMod()) {
                    player.addPP(-malusPP);  //players with the least stars lose pp

                }
            }

            if (playerStars == maxStars) {  //players with the most stars gain pp
                if (player.getDoubleRitualMod()) {
                    player.addPP(bonusPP * 2);
                } else {
                    player.addPP(bonusPP);
                }
            }
        }


            assertEquals( bonusPP-malusPP, players.get(0).getPP(), "Il player 1 non ottiene nulla");
            assertEquals( bonusPP-malusPP, players.get(1).getPP(), "player 2 ha il numero di stelle massimo, prende il bonus");
            assertEquals( bonusPP-malusPP, players.get(2).getPP(), "Player should gain 10 food");
    }


    @Test
    void testMinMaxBonus() {

        players.add(player1);
        players.add(player2);
        players.add(player3);

        player1.getTribe().addStars(10);
        player2.getTribe().addStars(100);
        player3.getTribe().addStars(1);


        int minStars = players.getFirst().getTribe().getStars();
        int maxStars = minStars;  //number of stars owned by the player(s) who has the most

        for (int i = 1; i < players.size(); i++) {
            int stars = players.get(i).getTribe().getStars();

            if (stars < minStars)
                minStars = stars;
            else if (stars > maxStars)
                maxStars = stars;
        }

        //apply effects to players, depending on their number of stars
        for (Player player : players) {
            int playerStars = player.getTribe().getStars();

            if (playerStars == minStars) {
                if (!player.getNoLossRitualMod()) {
                    player.addPP(-malusPP);  //players with the least stars lose pp

                }
            }

            if (playerStars == maxStars) {  //players with the most stars gain pp
                if (player.getDoubleRitualMod()) {
                    player.addPP(bonusPP * 2);
                } else {
                    player.addPP(bonusPP);
                }
            }
        }


        assertEquals( 0, players.get(0).getPP(), "Il player 1 non ottiene nulla");
        assertEquals( bonusPP, players.get(1).getPP(), "player 2 ha il numero di stelle massimo, prende il bonus");
        assertEquals( -malusPP, players.get(2).getPP(), "Player should gain 10 food");
    }

}