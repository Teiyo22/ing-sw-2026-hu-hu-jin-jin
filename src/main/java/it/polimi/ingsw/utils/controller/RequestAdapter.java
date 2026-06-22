package it.polimi.ingsw.utils.controller;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.requests.*;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.util.Map;

public class RequestAdapter extends GenericGsonAdapter<Request> {
    public RequestAdapter() {
        typeMap = Map.ofEntries(
            Map.entry("LoginRequest", LoginRequest.class),
            Map.entry("CreateLobbyRequest", CreateLobbyRequest.class),
            Map.entry("JoinLobbyRequest", JoinLobbyRequest.class),
            Map.entry("LeaveLobbyRequest", LeaveLobbyRequest.class),
            Map.entry("StartLobbyRequest", StartLobbyRequest.class),
            Map.entry("LobbyInfoRequest", LobbyInfoRequest.class),
            Map.entry("GetLeaderboardRequest", GetLeaderboardRequest.class),
            Map.entry("PlayerActionRequest", PlayerActionRequest.class),
            Map.entry("PingRequest", PingRequest.class)
        );
    }
}
