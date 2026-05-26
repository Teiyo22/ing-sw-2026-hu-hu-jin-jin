package it.polimi.ingsw.utils.controller;

import com.google.gson.*;
import it.polimi.ingsw.model.gameState.info.CardPickStateInfo;
import it.polimi.ingsw.model.gameState.info.ModelStateInfo;
import it.polimi.ingsw.model.gameState.info.GameEndStateInfo;
import it.polimi.ingsw.model.gameState.info.OfferPickStateInfo;

import java.lang.reflect.Type;

public class StateInfoDeserializer implements JsonDeserializer<ModelStateInfo> {
    @Override
    public ModelStateInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "CARD_PICK" -> context.deserialize(jsonObject, CardPickStateInfo.class);
            case "OFFER_PICK" -> context.deserialize(jsonObject, OfferPickStateInfo.class);
            case "GAME_END" -> context.deserialize(jsonObject, GameEndStateInfo.class);
            default -> throw new JsonParseException("Unknown state type: " + type);
        };
    }
}
