package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.utils.view.Formatter;

public class TUIOfferTrackSection implements TUISection {
    private Board board;

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Offer Track"));
        System.out.println(Formatter.line(String.format(
                " %-3s | %-25s | %-15s | %-15s | %-15s ",
                "idx", "Assigned Player", "Bonus Food", "Top Picks", "Bottom Picks")));

        for (int i = 0; i < board.getOfferTrack().length; i++)
            System.out.println(Formatter.line(String.format(" %-3d | %s", i, board.getOfferTrack()[i])));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        board = clientController.getBoard();
        return board != null;
    }
}
