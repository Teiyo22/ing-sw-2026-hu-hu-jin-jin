package it.polimi.ingsw.utils.controller;

import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.controller.client.action.OfferPickPlayerAction;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.util.Map;


public class PlayerActionAdapter extends GenericGsonAdapter<PlayerAction> {
    public PlayerActionAdapter() {
        typeMap = Map.ofEntries(
            Map.entry("CardPickPlayerAction", CardPickPlayerAction.class),
            Map.entry("OfferPickPlayerAction", OfferPickPlayerAction.class)
        );
    }
}
