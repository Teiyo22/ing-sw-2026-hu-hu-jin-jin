package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.BonusPPBuilding;
import it.polimi.ingsw.model.card.building.BuilderDoublePPBuilding;
import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.gameState.GameEndState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuilderDoublePPBuildingTest {
    private BuildingHandler buildingHandler;
    private BuilderDoublePPBuilding building;
    private Player player;
    private Player p2;
    private Game game;
    private ArrayList<Player> players;


    @BeforeEach
    void setUp() {
        buildingHandler = new BuildingHandler();
        building = new BuilderDoublePPBuilding(1 , false, 0, 0);
        player = new Player("X", Totem.BLACK);
        p2 = new Player("Y", Totem.RED);
        players = new ArrayList<>();

        players.add(player);
        players.add(p2);

        game = new Game(PlayerConfig.TWO, players);
        game.setLobbyState(new LobbyRunningState(new LobbyController(1, 2)));


        player.setTribe(new Tribe());

        building.register(player, buildingHandler);
    }

    @Test
    void testApplyEffect(){
        player.getTribe().addBuilder(new Builder(1, false, 0, 0));

        game.setGameState(new GameEndState(game, buildingHandler));
        int initialBonus = player.getTribe().getBuilderBonusPP();

        game.getGameState().update();

        buildingHandler.applyGameEndEffects();
        assertEquals(initialBonus*2, player.getTribe().getBuilderBonusPP());
    }





}