package it.polimi.ingsw.controller.common.messages;

public enum MessageType {
    SET_ID,
    CREATE_LOBBY,
    JOIN_LOBBY,
    ADD_CLIENT,
    ADD_PLAYER,
    LEAVE_LOBBY,
    REMOVE_CLIENT,
    REMOVE_PLAYER,
    START_LOBBY,
    STOP_LOBBY,
    WAITING_LOBBY,
    LOBBY_INFO,
    PICK_OFFER,
    PICK_CARDS,
    GET_RANK,
    GET_LEADERBOARD,
    OFFER_PICK,
    OFFER_RESOLUTION,
    EXTRA_ACTION,
    PING,
    ERROR,
    UPDATE_STATE
}
