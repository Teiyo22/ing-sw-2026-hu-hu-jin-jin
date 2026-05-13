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
            case "LOGIN" -> context.deserialize(jsonObject, LoginResponse.class);
            case "OFFER_PICK" -> context.deserialize(jsonObject, OfferPickResponse.class);
            case "OFFER_RESOLUTION" -> context.deserialize(jsonObject, OfferResolutionResponse.class);
            case "EXTRA_ACTION" -> context.deserialize(jsonObject, ExtraActionResponse.class);
            case "ROUND_END" -> context.deserialize(jsonObject, RoundEndResponse.class);
            case "GAME_END" -> context.deserialize(jsonObject, GameEndResponse.class);
            case "UPDATE_STATE" -> context.deserialize(jsonObject, UpdateStateResponse.class);
            case "CREATE_LOBBY" -> context.deserialize(jsonObject, CreateLobbyResponse.class);
            case "ADD_PLAYER" -> context.deserialize(jsonObject, AddPlayerResponse.class);
            case "ADD_LOBBY" -> context.deserialize(jsonObject, AddLobbyResponse.class);
            case "REMOVE_LOBBY" -> context.deserialize(jsonObject, RemoveLobbyResponse.class);
            case "REMOVE_CLIENT" -> context.deserialize(jsonObject, RemoveClientResponse.class);
            case "REMOVE_PLAYER" -> context.deserialize(jsonObject, RemovePlayerResponse.class);
            case "START_LOBBY" -> context.deserialize(jsonObject, StartLobbyResponse.class);
            case "STOP_LOBBY" -> context.deserialize(jsonObject, StopLobbyResponse.class);
            case "WAITING_LOBBY" -> context.deserialize(jsonObject, WaitingLobbyResponse.class);
            case "LOBBY_INFO" -> context.deserialize(jsonObject, LobbyInfoResponse.class);
            case "GET_LEADERBOARD" -> context.deserialize(jsonObject, GetLeaderboardResponse.class);
            case "SET_ID" -> context.deserialize(jsonObject, SetIDResponse.class);
            case "PING" -> context.deserialize(jsonObject, PingResponse.class);
            case "ERROR" -> context.deserialize(jsonObject, ErrorMessage.class);
            default -> throw new JsonParseException("Response type not found: " + type);
        };
    }
}
