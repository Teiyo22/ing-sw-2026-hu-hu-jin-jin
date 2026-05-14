package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;

import java.util.List;

public class TopRowSection extends RowSection {
    public TopRowSection(CardPicksListener listener) {
        super(listener);
    }

    @Override
    public List<AbstractBuilding> getBuildings(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getTopRow().getBuildingCards();
    }

    @Override
    public List<AbstractCharacter> getCharacters(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getTopRow().getCharacterCards();
    }

    @Override
    public List<AbstractEvent> getEvents(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getTopRow().getEventCards();
    }

    @Override
    public int getTotalPicks(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getTopRow().getPickableCardCount();
    }
}
