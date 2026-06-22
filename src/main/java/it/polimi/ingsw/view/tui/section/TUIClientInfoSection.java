package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.view.Formatter;

public class TUIClientInfoSection implements TUISection {
    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Client Info"));

        System.out.println(Formatter.line(String.format("Username: %-25s", clientController.getID())));
        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        return true;
    }
}
