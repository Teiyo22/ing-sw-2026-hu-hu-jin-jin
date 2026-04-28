package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.character.Collector;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RoundEndStateTest {
    private RoundEndState roundEndState;
    private List<Player> players;
    private Game game;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        game = new Game(PlayerConfig.TWO, players);
        roundEndState = new RoundEndState(game, new BuildingHandler());
        game.setGameState(roundEndState);
    }


    @Test
    void normalUpdateTest() {
        List<AbstractCharacter> topRowCharacters = new ArrayList<>(game.getBoard().getTopRow().getCharacterCards());
        List<AbstractEvent> topRowEvents = new ArrayList<>(game.getBoard().getTopRow().getEventCards());
        List<Sustenance> topRowSustenanceEvents = new ArrayList<>(game.getBoard().getTopRow().getSustenanceEventCards());

        roundEndState.update();

        assertEquals(topRowCharacters, game.getBoard().getBottomRow().getCharacterCards());
        assertEquals(topRowEvents, game.getBoard().getBottomRow().getEventCards());
        assertEquals(topRowSustenanceEvents, game.getBoard().getBottomRow().getSustenanceEventCards());
        assertEquals(game.getPlayerConfig().getNum() + 4,
                game.getBoard().getTopRow().getCharacterCards().size() +
                       game.getBoard().getTopRow().getEventCards().size() +
                        game.getBoard().getTopRow().getSustenanceEventCards().size());
        assertEquals(RoundStartState.class, game.getGameState().getClass());
    }

    @Test
    void changeEraUpdateTest() {
        Queue<AbstractCard> charEventCards = game.getBoard().getDeck().getCharEventCards();
        Queue<AbstractCard> extendedCharEventCards = new LinkedList<>();

        extendedCharEventCards.add(new Collector("Collector", 2, false));
        extendedCharEventCards.addAll(charEventCards);

        game.getBoard().getDeck().setCharEventCards(extendedCharEventCards);

        List<AbstractBuilding> topRowBuildings = new ArrayList<>(game.getBoard().getTopRow().getBuildingCards());
        roundEndState.update();

        assertEquals(topRowBuildings, game.getBoard().getBottomRow().getBuildingCards());
    }

    @Test
    void lastUpdateTest(){
        game.getBoard().getDeck().getCharEventCards().clear();
        roundEndState.update();
        assertEquals(GameEndState.class, game.getGameState().getClass());
    }
}