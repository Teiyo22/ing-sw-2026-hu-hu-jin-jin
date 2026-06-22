package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerTest {
    private ServerController server;

    @BeforeEach
    void setUp(){
        server = ServerController.getInstance();
        server.getLobbies().clear();
        server.getAllClients().clear();
    }

    @Test
    void createLobbyTest() {
        TestClient mario = new TestClient("Mario");

        server.getAllClients().put("Mario", mario);

        server.createLobby("Mario", 4, Totem.BLUE);

        assertFalse(server.getLobbies().isEmpty(), "La lobby dovrebbe essere creata");

        LobbyController controller = server.getLobbies().values().iterator().next();
        assertNotNull(controller);
        assertEquals(4, controller.getLobby().getSize());
        assertEquals(1,controller.getPlayers().size());
    }

    @Test
    void joinLobby_SuccessTest() {
        TestClient mario = new TestClient("Mario");
        server.getAllClients().put("Mario", mario);

        server.createLobby("Mario", 4, Totem.BLUE);

        server.joinLobby("Mario", server.getLobbies().values().iterator().next().getID(), Totem.RED);

        LobbyController controller = server.getLobbies().get(server.getLobbies().values().iterator().next().getID());
        assertEquals(1,controller.getPlayers().size());
    }

    @Test
    void joinLobby_LobbyNotFoundTest() {
        TestClient mario = new TestClient("Mario");
        server.getAllClients().put("Mario", mario);

        server.joinLobby("Mario", 999, Totem.RED);

        assertNull(mario.getCurrLobbyController(), "Il client non dovrebbe essere assegnato a nessuna lobby");
    }
    @Test
    void joinLobby_LobbyFullTest() {
        TestClient p1 = new TestClient("Mario");
        server.getAllClients().put("Mario", p1);

        server.createLobby("Mario", 2, Totem.BLUE);
        int id = server.getLobbies().values().iterator().next().getID();

        TestClient p2 = new TestClient("Pino");
        server.getAllClients().put("Pino", p2);
        server.joinLobby("Pino", id, Totem.RED);

        TestClient p3 = new TestClient("Intruso");
        server.getAllClients().put("Intruso", p3);
        server.joinLobby("Intruso", id, Totem.BLACK);

        LobbyController controller = server.getLobbies().get(id);
        assertEquals(2, controller.getPlayers().size(), "lobby piena");
        assertFalse(controller.getPlayers().containsKey(p3), "L'intruso non deve essere presente nella mappa dei giocatori");
    }

    @Test
    void joinLobby_TotemAlreadyTakenTest() {
        TestClient p1 = new TestClient("Mario");
        server.getAllClients().put("Mario", p1);

        server.createLobby("Mario", 2, Totem.BLUE);
        int id = server.getLobbies().values().iterator().next().getID();

        TestClient p2 = new TestClient("Pino");
        server.getAllClients().put("Pino", p2);

        server.joinLobby("Pino", id, Totem.BLUE);

        LobbyController controller = server.getLobbies().get(id);
        assertFalse(controller.getPlayers().containsKey(p2), "non deve essere nella lista dei giocatori");
    }

    @Test
    void leaveLobbyTest() {
        TestClient p1 = new TestClient("Mario");
        server.getAllClients().put("Mario", p1);
        server.createLobby("Mario", 2, Totem.BLUE);

        TestClient p2 = new TestClient("Pino");
        server.getAllClients().put("Pino", p2);
        server.joinLobby("Pino", server.getLobbies().values().iterator().next().getID(), Totem.RED);
        server.leaveLobby("Mario", server.getLobbies().values().iterator().next().getID());

        LobbyController controller = server.getLobbies().get(server.getLobbies().values().iterator().next().getID());
        assertFalse(controller.getPlayers().containsKey(p1), "Il giocatore dovrebbe essere stato rimosso");
        assertEquals(1, controller.getPlayers().size(), "La lobby dovrebbe essere vuota");
    }

    @Test
    void startLobby_Success_ShouldChangeStateOrRemove() {
        TestClient p1 = new TestClient("Mario");
        TestClient p2 = new TestClient("Pino");
        server.getAllClients().put("Mario", p1);
        server.getAllClients().put("Pino", p2);

        server.createLobby("Mario", 2, Totem.BLUE);
        int id = server.getLobbies().values().iterator().next().getID();

        server.joinLobby("Pino",id,Totem.WHITE);
        server.startLobby("Mario", id);

        LobbyController controller = server.getLobbies().get(id);
        assertTrue(controller.getState() instanceof LobbyRunningState);
        assertEquals(2,controller.getPlayers().size());
    }

    @Test
    void loginTest() {
        TestClient client = new TestClient("ID1");
        server.getAllClients().put("ID1", client);

        server.login("ID1", "Mario");

        assertTrue(server.getAllClients().containsKey("Mario"));
        assertTrue(client.isLoginConfirmed());
    }
}

