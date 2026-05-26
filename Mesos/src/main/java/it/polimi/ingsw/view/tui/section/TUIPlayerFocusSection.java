package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.Formatter;

public class TUIPlayerFocusSection implements Section {
    private Lobby currLobby;

    @Override
    public void render(ClientController clientController) {
        Player shownPlayer = currLobby.getShownPlayer();

        System.out.println();
        System.out.println(Formatter.separatorLine("Player Data"));
        System.out.println(Formatter.coloredLine("Name: " + shownPlayer.getName(), shownPlayer.getTotem().getColor()));
        System.out.println(Formatter.line("Prestige Points: " + shownPlayer.getPP()));
        System.out.println(Formatter.line("Food: " + shownPlayer.getFood()));

        System.out.println(Formatter.separatorLine("Characters"));
        System.out.println(Formatter.line("Inventors: "));
        for (InventorType i : InventorType.values())
            System.out.println(Formatter.line("- " + i + ": " + shownPlayer.getTribe().getNumInventorType(i)));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Builders: " + shownPlayer.getTribe().getBuilderCount()));
        System.out.println(Formatter.line("Buildings Discount: " + shownPlayer.getTribe().getBuilderDiscount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Hunter: " + shownPlayer.getTribe().getHunterCount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Shamans: " + shownPlayer.getTribe().getShamanCount()));
        System.out.println(Formatter.line("Stars: " + shownPlayer.getTribe().getStars()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Collectors: " + shownPlayer.getTribe().getCollectorCount()));
        System.out.println(Formatter.line("Sustenance Discount: " + shownPlayer.getTribe().getSustenanceDiscount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Artists: " + shownPlayer.getTribe().getArtistCount()));

        if (!shownPlayer.getBuildings().isEmpty())
            System.out.println(Formatter.separatorLine("Buildings"));
        for (AbstractBuilding b : shownPlayer.getBuildings())
            System.out.println(Formatter.line(b.toString()));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        currLobby = clientController.getCurrLobby();
        return currLobby != null && currLobby.getShownPlayer() != null;
    }
}
