package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.util.CardCache;

import java.util.List;

public class BottomRowSection extends RowSection {
    public BottomRowSection(CardPicksListener listener, CardCache cardCache) {
        super(listener, cardCache);
    }

    @Override
    public List<AbstractBuilding> getBuildings(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getBottomRow().getBuildingCards();
    }

    @Override
    public List<AbstractCharacter> getCharacters(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getBottomRow().getCharacterCards();
    }

    @Override
    public List<AbstractEvent> getEvents(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getBottomRow().getEventCards();
    }

    @Override
    public int getTotalPicks(ClientController clientController) {
        return clientController.getCurrLobby().getBoard().getOfferTrack()[
                clientController.getCurrLobby().getTurnState().getIndex()].getBottomRowPickable();
    }
}
