package it.polimi.ingsw.utils.controller;

import com.google.gson.*;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.common.messages.responses.*;

import java.lang.reflect.Type;

public class ResponseDeserializer implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        return switch (type) {
            case "PICK_CARDS" -> context.deserialize(jsonObject, PickCardsResponse.class);
            case "CREATE_LOBBY" -> context.deserialize(jsonObject, CreateLobbyResponse.class);
            case "JOIN_LOBBY" -> context.deserialize(jsonObject, JoinLobbyResponse.class);
            case "LEAVE_LOBBY" -> context.deserialize(jsonObject, LeaveLobbyResponse.class);
            case "START_LOBBY" -> context.deserialize(jsonObject, StartLobbyResponse.class);
            case "WAITING_LOBBY" -> context.deserialize(jsonObject, WaitingLobbyResponse.class);
            case "LOBBY_INFO" -> context.deserialize(jsonObject, LobbyInfoResponse.class);
            case "GET_RANK" -> context.deserialize(jsonObject, GetRankResponse.class);
            case "GET_LEADERBOARD" -> context.deserialize(jsonObject, GetLeaderboardResponse.class);
            case "SET_ID" -> context.deserialize(jsonObject, SetIDResponse.class);
            case "STOP_LOBBY" -> context.deserialize(jsonObject, StopLobbyMessage.class);
            case "PING" -> context.deserialize(jsonObject, PingMessage.class);
            case "DELETE_LOBBY" -> context.deserialize(jsonObject, DeleteLobbyMessage.class);
            default -> throw new JsonParseException("Response type not found: " + type);
        };
    }
}
