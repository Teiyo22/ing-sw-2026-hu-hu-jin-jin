package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.view.tui.Formatter;

public class RowSection implements Section {
    private final boolean isTop;

    public RowSection(boolean isTop) {
        this.isTop = isTop;
    }

    @Override
    public void render(ClientController clientController) {
        Row row = isTop ? clientController.getCurrLobby().getBoard().getTopRow()
                : clientController.getCurrLobby().getBoard().getBottomRow();

        System.out.println();

        if (isTop)
            System.out.println(Formatter.separatorLine("Top Row"));
        else
            System.out.println(Formatter.separatorLine("Bottom Row"));

        if (!row.getBuildingCards().isEmpty())
            System.out.println(Formatter.separatorLine("Buildings"));

        for (AbstractBuilding building : row.getBuildingCards())
            System.out.println(Formatter.line(building.toString()));

        if (!row.getCharacterCards().isEmpty())
            System.out.println(Formatter.separatorLine("Characters"));

        for (AbstractCharacter character : row.getCharacterCards())
            System.out.println(Formatter.line(character.toString()));

        if (!row.getSustenanceEventCards().isEmpty() || !row.getEventCards().isEmpty())
            System.out.println(Formatter.separatorLine("Events"));

        for (Sustenance sustenance : row.getSustenanceEventCards())
            System.out.println(Formatter.line(sustenance.toString()));
        for (AbstractEvent event : row.getEventCards())
            System.out.println(Formatter.line(event.toString()));

        System.out.println(Formatter.separatorLine(""));
    }
}
