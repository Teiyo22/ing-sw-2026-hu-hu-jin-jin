package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.view.tui.Formatter;

public class OrderTileSection implements Section {
    private Board board;

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Order Tile"));
        System.out.println(Formatter.line(String.format(" %-3s | %-25s | %-15s", "idx", "Assigned Player", "Food Delta")));

        for (int i = 0; i < board.getOrderTile().length; i++)
            System.out.println(Formatter.line(String.format(" %-3d | %s", i, board.getOrderTile()[i])));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        board = clientController.getBoard();
        return board != null;
    }
}
