package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.view.tui.Formatter;

public class RowSection implements Section {
    private final Row row;

    public RowSection(Row row) {
        this.row = row;
    }

    @Override
    public void render(ClientController clientController) {
        System.out.println(Formatter.separatorLine("Row"));

        for (AbstractBuilding building : row.getBuildingCards())
            System.out.println(Formatter.line(building.toString()));

        if (!row.getCharacterCards().isEmpty())
            System.out.println(Formatter.separatorLine(""));

        for (AbstractCharacter character : row.getCharacterCards())
            System.out.println(Formatter.line(character.toString()));

        if (!row.getSustenanceEventCards().isEmpty() || !row.getEventCards().isEmpty())
            System.out.println(Formatter.separatorLine(""));

        for (Sustenance sustenance : row.getSustenanceEventCards())
            System.out.println(Formatter.line(sustenance.toString()));
        for (AbstractEvent event : row.getEventCards())
            System.out.println(Formatter.line(event.toString()));
    }
}
