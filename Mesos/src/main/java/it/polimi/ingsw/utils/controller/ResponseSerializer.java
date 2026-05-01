package it.polimi.ingsw.utils.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.responses.*;

import java.lang.reflect.Type;

public class ResponseSerializer implements JsonSerializer<Response> {
    @Override
    public JsonElement serialize(Response src, Type typeOfSrc, JsonSerializationContext context) {
        MessageType type = src.getType();

        return switch (type) {
            case PICK_CARDS -> context.serialize(src, PickCardsResponse.class);
            case CREATE_LOBBY -> context.serialize(src, CreateLobbyResponse.class);
            case JOIN_LOBBY -> context.serialize(src, JoinLobbyResponse.class);
            case LEAVE_LOBBY -> context.serialize(src, LeaveLobbyResponse.class);
            case START_LOBBY -> context.serialize(src, StartLobbyResponse.class);
            case WAITING_LOBBY -> context.serialize(src, WaitingLobbyResponse.class);
            case LOBBY_INFO -> context.serialize(src, LobbyInfoResponse.class);
            case GET_RANK -> context.serialize(src, GetRankResponse.class);
            case GET_LEADERBOARD -> context.serialize(src, GetLeaderboardResponse.class);
            case SET_ID -> context.serialize(src, SetIDResponse.class);
            case STOP_LOBBY -> context.serialize(src, StopLobbyMessage.class);
            case PING -> context.serialize(src, PingMessage.class);
            case DELETE_LOBBY -> context.serialize(src, DeleteLobbyMessage.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
