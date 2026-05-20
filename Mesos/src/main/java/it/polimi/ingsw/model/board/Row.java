package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class Row implements Serializable {
    private List<Sustenance> sustenanceEventCards;
    private List<AbstractEvent> eventCards;
    private List<AbstractCharacter> characterCards;
    private List<AbstractBuilding> buildingCards;

    public Row() {
        this.sustenanceEventCards = new ArrayList<>();
        this.eventCards = new ArrayList<>();
        this.characterCards = new ArrayList<>();
        this.buildingCards = new ArrayList<>();
    }

    public Row(List<Sustenance> sustenanceEventCards, List<AbstractEvent> eventCards,
               List<AbstractCharacter> characterCards, List<AbstractBuilding> buildingCards) {
        this.sustenanceEventCards = sustenanceEventCards;
        this.eventCards = eventCards;
        this.characterCards = characterCards;
        this.buildingCards = buildingCards;
    }

    public void addSustenanceEvent(Sustenance event){ sustenanceEventCards.add(event); }

    public void addEvent(AbstractEvent event){ eventCards.add(event); }

    public void addCharacter(AbstractCharacter character){ characterCards.add(character); }

    public void addBuilding(AbstractBuilding building){ buildingCards.add(building); }

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

    public List<AbstractCard> getPickableCards () {
        List<AbstractCard> pickableCards = new ArrayList<>();
        pickableCards.addAll(characterCards);
        pickableCards.addAll(buildingCards);

        return pickableCards;
    }

    public Row copy() {
        return new Row(sustenanceCopy(), eventCopy(), characterCopy(), buildingCopy());
    }

    private List<Sustenance> sustenanceCopy() {
        return sustenanceEventCards.stream()
                .map(Sustenance::clone)
                .map(c -> (Sustenance) c)
                .toList();
    }

    private List<AbstractEvent> eventCopy() {
        return eventCards.stream()
                .map(AbstractEvent::clone)
                .map(c -> (AbstractEvent) c)
                .toList();
    }

    private List<AbstractBuilding> buildingCopy() {
        return buildingCards.stream()
                .map(AbstractBuilding::clone)
                .map(c -> (AbstractBuilding) c)
                .toList();
    }

    private List<AbstractCharacter> characterCopy() {
        return characterCards.stream()
                .map(AbstractCharacter::clone)
                .map(c -> (AbstractCharacter) c)
                .toList();
    }

    /**
     * Returns the number of remaining pickable cards in the row.
     * */
    public int getPickableCardCount() {
        return characterCards.size() + buildingCards.size();
    }
}
