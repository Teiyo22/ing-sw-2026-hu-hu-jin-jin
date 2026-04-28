package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.utils.model.CardConfig;
import it.polimi.ingsw.utils.model.CardFactory;
import it.polimi.ingsw.utils.model.ConfigLoader;
import it.polimi.ingsw.utils.model.DeckConfig;

import java.util.*;

public class Deck {
    private PlayerConfig playerConfig;
    private Board board;

    private Queue<AbstractCard> charEventCards;
    private Queue<AbstractCard> buildingCards;

    private int[] ageBuildingsCount;
    private int currentEra = 0;

    public Deck(PlayerConfig playerConfig, Board board) {
        this.playerConfig = playerConfig;
        this.board = board;
    }

    /**
     *  Initializes the deck objects based on the number of players.
     *  Each number is associated with a different deck config file.
     *  Each deck config file contains different configurations for the cards
     * */
    public void init() {
        DeckConfig deckConfig = new ConfigLoader().loadDeckConfig(playerConfig.getDeckConfigFile());
        ageBuildingsCount = deckConfig.getBuildingsCountPerAge()[playerConfig.getNum() - 2];
        initCards(deckConfig.getCardConfigs());
        initBuildingCards(deckConfig.getBuildingConfigs());
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


    /**
     * Initializes the character and event cards from the card configurations.
     *
     * @param configs contains a list of templates for the character/event cards and the associated number of copies.
     * */
    private void initCards(List<CardConfig> configs) {
        charEventCards = CardFactory.generateCards(configs, playerConfig.getNum());
    }


    /**
     * Initializes the building cards from the card configurations.
     * The buildings are separated from the character and event cards.
     *
     * @param configs contains a list of the templates for the building cards and the associated number of copies.
     * */
    private void initBuildingCards(List<CardConfig> configs) {
        buildingCards = CardFactory.generateBuildingCards(configs, ageBuildingsCount, playerConfig.getNum());
    }

    public Queue<AbstractCard> getCharEventCards() {
        return charEventCards;
    }

    public int getCurrentEra() {
        return currentEra;
    }
}
