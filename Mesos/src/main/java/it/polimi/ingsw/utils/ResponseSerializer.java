package it.polimi.ingsw.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.responses.PickCardsResponse;
import it.polimi.ingsw.controller.common.messages.responses.Response;

import java.lang.reflect.Type;

public class ResponseSerializer implements JsonSerializer<Response> {
    @Override
    public JsonElement serialize(Response src, Type typeOfSrc, JsonSerializationContext context) {
        MessageType type = src.getType();

        return switch (type) {
            case PICK_CARDS -> context.serialize(src, PickCardsResponse.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
