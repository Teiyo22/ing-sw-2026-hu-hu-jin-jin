package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.common.messages.responses.*;

import java.lang.reflect.Type;

public class ResponseSerializer implements JsonSerializer<Response> {
    @Override
    public JsonElement serialize(Response src, Type typeOfSrc, JsonSerializationContext context) {
        MessageType type = src.getType();

        return switch (type) {
            case UPDATE_MODEL -> context.serialize(src, UpdateModelResponse.class);
            case CREATE_LOBBY -> context.serialize(src, CreateLobbyResponse.class);
            case ADD_CLIENT -> context.serialize(src, AddClientResponse.class);
            case ADD_PLAYER -> context.serialize(src, AddPlayerResponse.class);
            case REMOVE_CLIENT -> context.serialize(src, RemoveClientResponse.class);
            case REMOVE_PLAYER -> context.serialize(src, RemovePlayerResponse.class);
            case START_LOBBY -> context.serialize(src, StartLobbyResponse.class);
            case STOP_LOBBY -> context.serialize(src, StopLobbyResponse.class);
            case WAITING_LOBBY -> context.serialize(src, WaitingLobbyResponse.class);
            case LOBBY_INFO -> context.serialize(src, LobbyInfoResponse.class);
            case GET_RANK -> context.serialize(src, GetRankResponse.class);
            case GET_LEADERBOARD -> context.serialize(src, GetLeaderboardResponse.class);
            case SET_ID -> context.serialize(src, SetIDResponse.class);
            case PING -> context.serialize(src, PingResponse.class);
            case ERROR -> context.serialize(src, ErrorMessage.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
