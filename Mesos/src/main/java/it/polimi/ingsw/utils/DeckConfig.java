package it.polimi.ingsw.utils;

import java.util.List;

public class DeckConfig {
    private int playerCount;
    private List<Integer> buildingsCountPerAge;
    private List<CardConfig> buildingConfigs;
    private List<CardConfig> cardConfigs;

    public List<CardConfig> getCardConfigs() {
        return cardConfigs;
    }

    public List<CardConfig> getBuildingConfigs() {
        return buildingConfigs;
    }

    public List<Integer> getBuildingsCountPerAge() {
        return buildingsCountPerAge;
    }
}
