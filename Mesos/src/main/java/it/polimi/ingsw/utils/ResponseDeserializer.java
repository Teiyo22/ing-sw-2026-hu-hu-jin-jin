package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.controller.common.messages.responses.PickCardsResponse;
import it.polimi.ingsw.controller.common.messages.responses.Response;

import java.lang.reflect.Type;

public class ResponseDeserializer implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "PICK_CARDS" -> context.deserialize(jsonObject, PickCardsResponse.class);
            default -> throw new JsonParseException("Response type not found: " + type);
        };
    }
}
