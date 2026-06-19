package it.polimi.ingsw.utils.controller;

import it.polimi.ingsw.controller.client.info.*;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.util.Map;

public class StateInfoAdapter extends GenericGsonAdapter<ModelStateInfo> {
    public StateInfoAdapter() {
        typeMap = Map.ofEntries(
            Map.entry("CardPickStateInfo", CardPickStateInfo.class),
            Map.entry("OfferPickStateInfo", OfferPickStateInfo.class),
            Map.entry("GameEndStateInfo", GameEndStateInfo.class)
        );
    }
}
