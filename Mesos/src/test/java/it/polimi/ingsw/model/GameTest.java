package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.card.building.*;

import java.util.ArrayList;
import java.util.List;

public class GameTest {
    private Game g;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));
        players.add(new Player("Carlo", Totem.WHITE));
        g = new Game(PlayerConfig.FIVE, players);
    }

    @Test
    void setGameStateTest() {
        BuildingHandler buildingHandler= new BuildingHandler();
        GameState state = new GameStartState(g, buildingHandler);
        g.setGameState(state);
        assertEquals(GameStartState.class, g.getGameState().getClass());
    }

    @Test
    void pickTest() {
        List<Pickable> topPicks = new ArrayList<>();
        List<Pickable> bottomPicks = new ArrayList<>();

        int topRowInitialSize = g.getBoard().getTopRow().getCharacterCards().size();
        int bottomRowInitialSize = g.getBoard().getBottomRow().getCharacterCards().size();
        AbstractCharacter topCard = g.getBoard().getTopRow().getCharacterCards().get(0);
        AbstractCharacter bottomCard = g.getBoard().getBottomRow().getCharacterCards().get(0);

        topPicks.add(topCard);
        bottomPicks.add(bottomCard);

        g.pick(players.get(0), topPicks, bottomPicks);
        assertEquals(topRowInitialSize - 1, g.getBoard().getTopRow().getCharacterCards().size());
        assertEquals(bottomRowInitialSize - 1, g.getBoard().getBottomRow().getCharacterCards().size());
        assertEquals(2, players.get(0).getTribe().getTribeSize());
    }

    @Test
    void assignToTest() {
        OfferTile offerTile = g.getBoard().getOfferTrack()[0];
        g.assignTo(players.get(0), offerTile);

        assertEquals(players.get(0), offerTile.getAssignedPlayer());
    }
}

