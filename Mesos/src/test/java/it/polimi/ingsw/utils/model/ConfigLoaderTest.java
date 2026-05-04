package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.PlayerConfig;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {
    private ConfigLoader configLoader;
    private DeckConfig deckConfig;
    private Queue<AbstractCard> charEventCards;
    private Queue<AbstractCard> buildingCards;


    @Test
    public void loadDeckTest(){
        configLoader = new ConfigLoader();
        deckConfig = configLoader.loadDeckConfig(PlayerConfig.FIVE.getDeckConfigFile());
        charEventCards = CardFactory.generateCards(deckConfig.getCardConfigs(),  PlayerConfig.FIVE.getNum());
        buildingCards = CardFactory.generateBuildingCards(deckConfig.getBuildingConfigs(),
                deckConfig.getBuildingsCountPerAge()[PlayerConfig.FIVE.getNum() - 2],
                PlayerConfig.FIVE.getNum());

        assertEquals(96, charEventCards.size());
        assertEquals(10, buildingCards.size());
    }
}