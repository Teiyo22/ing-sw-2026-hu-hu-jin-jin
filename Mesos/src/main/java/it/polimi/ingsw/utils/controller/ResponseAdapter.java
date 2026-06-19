package it.polimi.ingsw.utils.controller;

import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.util.Map;

public class ResponseAdapter extends GenericGsonAdapter<Response> {
    public ResponseAdapter() {
        typeMap = Map.ofEntries(
            Map.entry("LoginResponse", LoginResponse.class),
            Map.entry("OfferPickResponse", OfferPickResponse.class),
            Map.entry("OfferResolutionResponse", OfferResolutionResponse.class),
            Map.entry("RoundEndResponse", RoundEndResponse.class),
            Map.entry("GameEndResponse", GameEndResponse.class),
            Map.entry("UpdateStateResponse", UpdateStateResponse.class),
            Map.entry("CreateLobbyResponse", CreateLobbyResponse.class),
            Map.entry("AddPlayerResponse", AddPlayerResponse.class),
            Map.entry("AddLobbyResponse", AddLobbyResponse.class),
            Map.entry("RemoveLobbyResponse", RemoveLobbyResponse.class),
            Map.entry("UpdateLobbyResponse", UpdateLobbyResponse.class),
            Map.entry("RemoveClientResponse", RemoveClientResponse.class),
            Map.entry("RemovePlayerResponse", RemovePlayerResponse.class),
            Map.entry("StartLobbyResponse", StartLobbyResponse.class),
            Map.entry("StopLobbyResponse", StopLobbyResponse.class),
            Map.entry("WaitingLobbyResponse", WaitingLobbyResponse.class),
            Map.entry("LobbyInfoResponse", LobbyInfoResponse.class),
            Map.entry("GetLeaderboardResponse", GetLeaderboardResponse.class),
            Map.entry("SetIDResponse", SetIDResponse.class),
            Map.entry("PingResponse", PingResponse.class),
            Map.entry("ErrorMessage", ErrorMessage.class),
            Map.entry("EventResultMessage", EventResultMessage.class)
        );
    }
}
