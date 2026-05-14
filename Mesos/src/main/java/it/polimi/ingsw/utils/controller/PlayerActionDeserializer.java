package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.model.action.PlayerAction;

import java.lang.reflect.Type;

public class PlayerActionDeserializer implements JsonDeserializer<PlayerAction> {
    @Override
    public PlayerAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("type").getAsString();
        return switch (type) {
            case "CARD_PICK" -> context.deserialize(json, it.polimi.ingsw.model.action.CardPickPlayerAction.class);
            case "OFFER_PICK" -> context.deserialize(json, it.polimi.ingsw.model.action.OfferPickPlayerAction.class);
            default -> throw new JsonParseException("Unknown action type: " + type);
        };
    }
}
