package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.requests.*;

import java.lang.reflect.Type;

public class RequestSerializer implements JsonSerializer<Request> {
    @Override
    public JsonElement serialize(Request src, Type typeOfSrc, JsonSerializationContext context) {
        MessageType type = src.getType();

        return switch (type) {
            case LOGIN -> context.serialize(src, LoginRequest.class);
            case CREATE_LOBBY -> context.serialize(src, CreateLobbyRequest.class);
            case JOIN_LOBBY -> context.serialize(src, JoinLobbyRequest.class);
            case LEAVE_LOBBY -> context.serialize(src, LeaveLobbyRequest.class);
            case START_LOBBY -> context.serialize(src, StartLobbyRequest.class);
            case LOBBY_INFO -> context.serialize(src, LobbyInfoRequest.class);
            case GET_LEADERBOARD -> context.serialize(src, GetLeaderboardRequest.class);
            case PLAYER_ACTION -> context.serialize(src, PlayerActionRequest.class);
            case PING -> context.serialize(src, PingRequest.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
