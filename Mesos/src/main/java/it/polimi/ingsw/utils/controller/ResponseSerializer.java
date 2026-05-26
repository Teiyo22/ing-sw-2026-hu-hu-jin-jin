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
            case LOGIN -> context.serialize(src, LoginResponse.class);
            case OFFER_PICK -> context.serialize(src, OfferPickResponse.class);
            case OFFER_RESOLUTION -> context.serialize(src, OfferResolutionResponse.class);
            case GAME_END -> context.serialize(src, GameEndResponse.class);
            case UPDATE_STATE -> context.serialize(src, UpdateStateResponse.class);
            case CREATE_LOBBY -> context.serialize(src, CreateLobbyResponse.class);
            case ADD_PLAYER -> context.serialize(src, AddPlayerResponse.class);
            case ADD_LOBBY -> context.serialize(src, AddLobbyResponse.class);
            case REMOVE_LOBBY -> context.serialize(src, RemoveLobbyResponse.class);
            case UPDATE_LOBBY -> context.serialize(src, UpdateLobbyResponse.class);
            case REMOVE_CLIENT -> context.serialize(src, RemoveClientResponse.class);
            case REMOVE_PLAYER -> context.serialize(src, RemovePlayerResponse.class);
            case START_LOBBY -> context.serialize(src, StartLobbyResponse.class);
            case STOP_LOBBY -> context.serialize(src, StopLobbyResponse.class);
            case WAITING_LOBBY -> context.serialize(src, WaitingLobbyResponse.class);
            case LOBBY_INFO -> context.serialize(src, LobbyInfoResponse.class);
            case GET_LEADERBOARD -> context.serialize(src, GetLeaderboardResponse.class);
            case SET_ID -> context.serialize(src, SetIDResponse.class);
            case PING -> context.serialize(src, PingResponse.class);
            case ERROR -> context.serialize(src, ErrorMessage.class);
            case EVENT_RESULT -> context.serialize(src, EventResultMessage.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}
