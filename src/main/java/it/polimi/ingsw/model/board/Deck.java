package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.utils.model.CardConfig;
import it.polimi.ingsw.utils.model.CardFactory;
import it.polimi.ingsw.utils.model.ConfigLoader;
import it.polimi.ingsw.utils.model.DeckConfig;

import java.io.Serializable;
import java.util.*;

public class Deck {
    private Queue<AbstractCard> charEventCards;
    private Queue<AbstractCard> buildingCards;

    private int[] ageBuildingsCount;
    private int currentEra;

    /** Creates the deck based on the number of players.
     * The DeckConfig contains the arrays of number of cards per era ordered by number of players,
     * from 2 to 5; thus to obtain the correct one 2 is subtracted from number of players.*/
    public Deck(PlayerConfig playerConfig) {
        DeckConfig deckConfig = new ConfigLoader().loadDeckConfig(playerConfig.getDeckConfigFile());
        ageBuildingsCount = deckConfig.getBuildingsCountPerAge()[playerConfig.getNum() - 2];
        currentEra = 0;

        charEventCards = CardFactory.generateCards(deckConfig.getCardConfigs(), playerConfig.getNum());
        buildingCards = CardFactory.generateBuildingCards(deckConfig.getBuildingConfigs(), ageBuildingsCount, playerConfig.getNum());
    }

    public Deck() {}

    public Deck deepCopy() {
        Deck deck = new Deck();
        deck.charEventCards = new LinkedList<>(charEventCards);
        deck.buildingCards = new LinkedList<>(buildingCards);
        deck.ageBuildingsCount = ageBuildingsCount.clone();
        deck.currentEra = currentEra;
        return deck;
    }

    /**
     * Draws a certain number of character/event cards.
     *
     * @param num Number of drawn cards
     * @return List of character/event cards.
     * */
    public List<AbstractCard> drawCards(int num) {
        List<AbstractCard> cards = new ArrayList<>();

        for(int i = 0; i < num; i++) {
            if(!charEventCards.isEmpty())
                cards.add(charEventCards.poll());
            else {
                System.out.println("Attempting to draw from empty deck");
                break;
            }
        }

        return cards;
    }

    /**
     * Draws a certain number of building cards based on the current age.
     *
     * @return List of building cards.
     * */
    public List<AbstractCard> drawBuildingCards() {
        List<AbstractCard> buildings = new ArrayList<>();

        for (int i = 0; i < ageBuildingsCount[currentEra]; i++) {
            if (!buildingCards.isEmpty())
                buildings.add(buildingCards.poll());
            else {
                System.out.println("Attempting to draw from empty deck");
                break;
            }
        }

        return buildings;
    }

    /**
     * Updates the current age.
     * */
    public void changeEra() {
        this.currentEra += 1;
    }

    public Queue<AbstractCard> getCharEventCards() {
        return charEventCards;
    }

    public void setCharEventCards(Queue<AbstractCard> charEventCards) {
        this.charEventCards = charEventCards;
    }

    public Queue<AbstractCard> getBuildingCards() {
        return buildingCards;
    }

    public void setBuildingCards(Queue<AbstractCard> buildingCards) {
        this.buildingCards = buildingCards;
    }

    public int getCurrentEra() {
        return currentEra + 1;
    }
}
