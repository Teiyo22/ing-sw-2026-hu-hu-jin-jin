package it.polimi.ingsw.utils.controller;

import com.google.gson.*;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.requests.*;

import java.lang.reflect.Type;

public class RequestDeserializer implements JsonDeserializer<Request> {
    @Override
    public Request deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "PICK_CARDS" -> context.deserialize(jsonObject, PickCardsRequest.class);
            case "CREATE_LOBBY" -> context.deserialize(jsonObject, CreateLobbyRequest.class);
            case "JOIN_LOBBY" -> context.deserialize(jsonObject, JoinLobbyRequest.class);
            case "LEAVE_LOBBY" -> context.deserialize(jsonObject, LeaveLobbyRequest.class);
            case "START_LOBBY" -> context.deserialize(jsonObject, StartLobbyRequest.class);
            case "WAITING_LOBBY" -> context.deserialize(jsonObject, WaitingLobbyRequest.class);
            case "LOBBY_INFO" -> context.deserialize(jsonObject, LobbyInfoRequest.class);
            case "GET_LEADERBOARD" -> context.deserialize(jsonObject, GetLeaderboardRequest.class);
            case "PICK_OFFER" -> context.deserialize(jsonObject, PickOfferRequest.class);
            case "PING" -> context.deserialize(jsonObject, PingRequest.class);
            default -> throw new JsonParseException("Request type not found: " + type);
        };
    }
}
