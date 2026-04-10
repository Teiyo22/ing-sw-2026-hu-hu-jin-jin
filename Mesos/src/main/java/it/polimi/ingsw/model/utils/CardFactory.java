package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.card.AbstractCard;

import java.util.*;

public class CardFactory {
    final static int maxEra = 3;

    public static Queue<AbstractCard> generateCards(List<CardConfig> configs) {
        List<List<AbstractCard>> cardsDividedByEra = new ArrayList<>();
        Queue<AbstractCard> cards = new LinkedList<>();

        for(int i = 0; i < maxEra + 1; i++)
            cardsDividedByEra.add(new ArrayList<>());

        for (CardConfig config : configs)
            for (int i = 0; i < config.getQuantity(); i++) {
                AbstractCard card = config.getCard().copy();

                if(card.isFinal())
                    cardsDividedByEra.getLast().add(card);
                else if(card.getEra() <= maxEra)
                    cardsDividedByEra.get(card.getEra() - 1).add(card);
            }

        for(int i = 0; i < maxEra + 1; i++) {
            Collections.shuffle(cardsDividedByEra.get(i));
            cards.addAll(cardsDividedByEra.get(i));
        }

        return cards;
    }

    public static Queue<AbstractCard> generateBuildingCards(List<CardConfig> configs, List<Integer> buildingsCountPerAge) {
        List<List<AbstractCard>> buildingsDividedByEra = new ArrayList<>();
        Queue<AbstractCard> buildings = new LinkedList<>();

        for(int i = 0; i < maxEra; i++)
            buildingsDividedByEra.add(new ArrayList<>());

        for(CardConfig config: configs)
            for(int i = 0; i < config.getQuantity(); i++) {
                AbstractCard building = config.getCard().copy();

                if(building.getEra() <= maxEra)
                    buildingsDividedByEra.get(building.getEra() - 1).add(building);
            }

        for(int i = 0; i < maxEra; i++) {
            Collections.shuffle(buildingsDividedByEra.get(i));
            for(int j = 0; i < buildingsCountPerAge.get(i); i++)
                buildings.add(buildingsDividedByEra.get(i).get(j));
        }

        return buildings;
    }
}
