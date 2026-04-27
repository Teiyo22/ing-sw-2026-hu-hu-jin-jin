package it.polimi.ingsw.utils;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DeckConfig {
    @Expose private List<Integer> buildingsCountPerAge;
    @Expose private List<CardConfig> buildingConfigs;
    @Expose private List<CardConfig> cardConfigs;

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
