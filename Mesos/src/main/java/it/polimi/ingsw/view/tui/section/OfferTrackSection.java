package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.tui.Formatter;

public class OfferTrackSection implements Section {
    @Override
    public void render(ClientController clientController) {
        OfferTile[] offerTrack = clientController.getBoard().getOfferTrack();

        System.out.println(Formatter.separatorLine("Offer Track"));
        System.out.println(Formatter.line(String.format(
                " %-3s | %-25s | %-15s | %-15s | %-15s ",
                "idx", "Assigned Player", "Bonus Food", "Top Picks", "Bottom Picks")));

        for (int i = 0; i < offerTrack.length; i++)
            System.out.println(Formatter.line(String.format(" %-3d | %s", i, offerTrack[i])));
    }
}
