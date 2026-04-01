package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;

import java.util.List;

public class Row {
    private final List<Sustenance> sustenanceEventCards;
    private final List<AbstractEvent> eventCards;
    private final List<AbstractCharacter> characterCards;
    private final List<AbstractBuilding> buildingCards;

    public Row() {

    }

    public List<Sustenance> getSustenanceEventCards() {
        return sustenanceEventCards;
    }

    public List<AbstractEvent> getEventCards() {
        return eventCards;
    }

    public List<AbstractCharacter> getCharacterCards() {
        return characterCards;
    }

    public List<AbstractBuilding> getBuildingCards() {
        return buildingCards;
    }

    public int getPickableCardCount() {

    }
}
