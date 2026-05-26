package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.card.event.EventResult;

import java.io.Serializable;
import java.util.List;

public class EventResultMessage extends Response implements Serializable {
    private int lobbyID;
    private String context;
    private List<EventResult> results;

    public EventResultMessage(int lobbyID, String context, List<EventResult> results) {
        this.type = MessageType.EVENT_RESULT;
        this.lobbyID = lobbyID;
        this.context = context;
        this.results = results;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.showEventResults(this);
    }

    public List<EventResult> getResults() {
        return results;
    }

    public String getContext() {
        return context;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}
