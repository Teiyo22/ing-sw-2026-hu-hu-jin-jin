package it.polimi.ingsw.utils.controller;

import com.google.gson.*;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;

import java.lang.reflect.Type;

public class StateInfoDeserializer implements JsonDeserializer<ModelStateInfo> {
    @Override
    public ModelStateInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "CARD_PICK" -> context.deserialize(jsonObject, it.polimi.ingsw.controller.common.info.CardPickStateInfo.class);
            case "OFFER_PICk" -> context.deserialize(jsonObject, it.polimi.ingsw.controller.common.info.OfferPickStateInfo.class);
            case "GAME_END" -> context.deserialize(jsonObject, it.polimi.ingsw.controller.common.info.GameEndStateInfo.class);
            default -> throw new JsonParseException("Unknown state type: " + type);
        };
    }
}
