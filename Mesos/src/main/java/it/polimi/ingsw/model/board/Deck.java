package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.utils.*;

import java.util.*;

public class Deck {
    final private int gameSize;
    private final Board board;

    private Queue<AbstractCard> charEventCards;
    private Queue<AbstractCard> buildingCards;

    private List<Integer> ageBuildingsCount;
    private int currentAge = 0;

    public Deck(int gameSize, Board board) {
        this.gameSize = gameSize;
        this.board = board;
    }

    /**
     *  Initializes the deck objects based on the number of players.
     *  Each number is associated to a different deck config file.
     *  Each deck config file contains different configurations for the cards
     * */
    public void init() {
        DeckConfig deckConfig = new DeckConfigLoader().load("tmp");
        ageBuildingsCount = deckConfig.getBuildingsCountPerAge();
        initCards(deckConfig.getCardConfigs());
        initBuildingCards(deckConfig.getBuildingConfigs());
    }

    /**
     * Draws a certain number of character/event cards.
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
     * @return List of building cards.
     * */
    public List<AbstractCard> drawBuildingsCards() {
        List<AbstractCard> buildings = new ArrayList<>();

        for (int i = 0; i < ageBuildingsCount.get(currentAge); i++) {
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
    public void changeAge() {
        this.currentAge += 1;
    }


    /**
     * Initializes the character/event cards list of list card configurations
     * */
    private void initCards(List<CardConfig> configs) {
        charEventCards = CardFactory.generateCards(configs);
    }


    /**
     * Initializes the building cards list of list card configurations
     * */
    private void initBuildingCards(List<CardConfig> configs) {
        buildingCards = CardFactory.generateBuildingCards(configs, ageBuildingsCount);
    }
}
