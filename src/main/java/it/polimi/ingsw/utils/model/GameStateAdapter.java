package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.utils.GenericGsonAdapter;

import java.util.Map;

public class GameStateAdapter extends GenericGsonAdapter<GameState> {
    public GameStateAdapter() {
        typeMap = Map.ofEntries(
            Map.entry("GameStartState", GameStartState.class),
            Map.entry("RoundStartState", RoundStartState.class),
            Map.entry("RoundActionState", RoundActionState.class),
            Map.entry("RoundEndState", RoundEndState.class),
            Map.entry("ExtraActionState", ExtraActionState.class),
            Map.entry("GameEndState", GameEndState.class)
        );
    }
}
