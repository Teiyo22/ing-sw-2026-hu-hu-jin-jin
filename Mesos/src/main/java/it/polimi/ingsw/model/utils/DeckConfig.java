package it.polimi.ingsw.model.utils;

import java.util.List;

public class DeckConfig {
    private int playerCount;
    private List<Integer> buildingsCountPerAge;
    private List<BuildingConfig> buildingConfigs;
    private List<CardConfig> cardConfigs;

    public List<CardConfig> getCardConfigs() {
        return cardConfigs;
    }

    public List<BuildingConfig> getBuildingConfigs() {
        return buildingConfigs;
    }

    public List<Integer> getBuildingsCountPerAge() {
        return buildingsCountPerAge;
    }
}
