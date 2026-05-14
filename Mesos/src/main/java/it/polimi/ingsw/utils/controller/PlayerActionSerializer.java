package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.model.action.ActionType;
import it.polimi.ingsw.model.action.PlayerAction;

import java.lang.reflect.Type;

public class PlayerActionSerializer implements JsonSerializer<PlayerAction> {
    @Override
    public JsonElement serialize(PlayerAction src, Type typeOfSrc, JsonSerializationContext context) {
        ActionType type = src.getType();
        return switch (type) {
            case CARD_PICK -> context.serialize(src, it.polimi.ingsw.model.action.CardPickPlayerAction.class);
            case OFFER_PICK -> context.serialize(src, it.polimi.ingsw.model.action.OfferPickPlayerAction.class);
        };
    }
}
