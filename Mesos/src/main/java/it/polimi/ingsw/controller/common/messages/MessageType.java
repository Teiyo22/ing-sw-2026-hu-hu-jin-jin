package it.polimi.ingsw.controller.common.messages;

public enum MessageType {
    LOGIN,
    SET_ID,
    CREATE_LOBBY,
    JOIN_LOBBY,
    ADD_PLAYER,
    ADD_LOBBY,
    REMOVE_LOBBY,
    UPDATE_LOBBY,
    LEAVE_LOBBY,
    REMOVE_CLIENT,
    REMOVE_PLAYER,
    START_LOBBY,
    STOP_LOBBY,
    WAITING_LOBBY,
    LOBBY_INFO,
    PLAYER_ACTION,
    GET_LEADERBOARD,
    OFFER_PICK,
    OFFER_RESOLUTION,
    EXTRA_ACTION,
    ROUND_END,
    GAME_END,
    PING,
    ERROR,
    UPDATE_STATE
}
