package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.command.DisconnectCommand;
import it.polimi.ingsw.view.command.GetWaitingLobbiesCommand;
import it.polimi.ingsw.view.command.LeaveLobbyCommand;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.List;
import java.util.stream.IntStream;

public class GamePlayScreen implements Screen {
    private final ClientController clientController;

    private Lobby currLobby;
    private List<Player> players;
    private Board board;

    private String errorMsg = "";
    private String nextInputMsg = "Enter action: ";

    public GamePlayScreen(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void render() {
        Formatter.clearScreen();
        printAvailableActions();
        printPlayers();
        printOrderTile();
        printRow(board.getTopRow());
        printOfferTrack();
        printRow(board.getBottomRow());

        System.out.println(Formatter.formatSeparatorLine(""));
        System.out.println("\u001B[1m\u001B[31m" + errorMsg + "\u001B[0m");
        System.out.print(nextInputMsg);
    }

    @Override
    public void handleInput(String input) {
        switch (input.toLowerCase()) {
            case "0", "disconnect" -> new DisconnectCommand(clientController).execute();
            case "1", "leave" -> handleLeave();
            case "2", "show" -> handleShow();
            default -> handleInvalidInput();
        }
    }

    @Override
    public void update() {
        currLobby = clientController.getCurrLobby();
        players = clientController.getPlayers();
        board = clientController.getBoard();
    }

    @Override
    public void onExit() {

    }

    private void printAvailableActions() {
        System.out.println(Formatter.formatSeparatorLine("Lobby Selection"));
        System.out.println(Formatter.formatLine("Available actions:"));
        System.out.println(Formatter.formatLine("0. Disconnect"));
        System.out.println(Formatter.formatLine("1. Leave"));
        System.out.println(Formatter.formatLine("2. Show"));
    }

    private void printPlayers() {
        System.out.println(Formatter.formatSeparatorLine("Players"));

        for(Player p : players)
            System.out.println(Formatter.formatColoredLine(
                    String.format("%-25s | Food: %-3d | PP: %-3d",
                            p.getName(), p.getFood(), p.getPP()), p.getTotem().getColor()));
    }

    private void printOrderTile() {
        System.out.println(Formatter.formatSeparatorLine("Order Tile"));
        System.out.println(Formatter.formatLine(String.format(" %-3s | %-25s | %-15s", "idx", "Assigned Player", "Food Delta")));

        for (int i = 0; i < board.getOrderTile().length; i++)
            System.out.println(Formatter.formatLine(String.format(" %-3d | %s", i, board.getOrderTile()[i])));
    }

    private void printRow(Row row) {
        System.out.println(Formatter.formatSeparatorLine("Row"));

        for (AbstractBuilding building : row.getBuildingCards())
            System.out.println(Formatter.formatLine(building.toString()));

        if (!row.getCharacterCards().isEmpty())
            System.out.println(Formatter.formatSeparatorLine(""));

        for (AbstractCharacter character: row.getCharacterCards())
            System.out.println(Formatter.formatLine(character.toString()));

        if (!row.getSustenanceEventCards().isEmpty() || !row.getEventCards().isEmpty())
            System.out.println(Formatter.formatSeparatorLine(""));

        for (Sustenance sustenance: row.getSustenanceEventCards())
            System.out.println(Formatter.formatLine(sustenance.toString()));
        for (AbstractEvent event : row.getEventCards())
            System.out.println(Formatter.formatLine(event.toString()));
    }

    private void printOfferTrack() {
        OfferTile[] offerTrack = board.getOfferTrack();

        System.out.println(Formatter.formatSeparatorLine("Offer Track"));
        System.out.println(Formatter.formatLine(String.format(
                " %-3s | %-25s | %-15s | %-15s | %-15s ",
                "idx", "Assigned Player", "Bonus Food", "Top Picks", "Bottom Picks")));

        for (int i = 0; i < offerTrack.length; i++)
            System.out.println(Formatter.formatLine(String.format(" %-3d | %s", i, offerTrack[i])));

    }

    private void handleLeave() {
        if (currLobby == null) {
            handleInvalidInput();
            return;
        }
        new LeaveLobbyCommand(clientController, currLobby.getLobbyID()).execute();

        resetMsg();
        render();
    }

    private void handleShow() {
        render();
    }

    private void handleInvalidInput() {
        errorMsg = "Invalid command.";
        render();
    }

    private void resetMsg() {
        errorMsg = "";
        nextInputMsg = "Enter action: ";
    }
}
