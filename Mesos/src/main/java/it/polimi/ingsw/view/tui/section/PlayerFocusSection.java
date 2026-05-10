package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.InventorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.Map;

public class PlayerFocusSection implements Section {
    @Override
    public void render(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();

        Player player = currLobby.getShownPlayer();

        System.out.println();
        System.out.println(Formatter.separatorLine("Player Data"));
        System.out.println(Formatter.coloredLine("Name: " + player.getName(), player.getTotem().getColor()));
        System.out.println(Formatter.line("Prestige Points: " + player.getPP()));
        System.out.println(Formatter.line("Food: " + player.getFood()));

        System.out.println(Formatter.separatorLine("Characters"));
        System.out.println(Formatter.line("Inventors: "));
        for (InventorType i : InventorType.values())
            System.out.println(Formatter.line("- " + i + ": " + player.getTribe().getNumInventorType(i)));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Builders: " + player.getTribe().getBuilderCount()));
        System.out.println(Formatter.line("Buildings Discount: " + player.getTribe().getBuilderDiscount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Hunter: " + player.getTribe().getHunterCount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Shamans: " + player.getTribe().getShamanCount()));
        System.out.println(Formatter.line("Stars: " + player.getTribe().getStars()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Collectors: " + player.getTribe().getCollectorCount()));
        System.out.println(Formatter.line("Sustenance Discount: " + player.getTribe().getSustenanceDiscount()));

        System.out.println(Formatter.line(""));
        System.out.println(Formatter.line("Artists: " + player.getTribe().getArtistCount()));

        if (!player.getBuildings().isEmpty())
            System.out.println(Formatter.separatorLine("Buildings"));
        for (AbstractBuilding b : player.getBuildings())
            System.out.println(Formatter.line(b.toString()));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();

        return currLobby != null &&
               currLobby.isShownPlayer();
    }
}
