package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.util.Map;

public class LobbyListSection extends GUISection {
    private final JList<Lobby> lobbies;
    private final DefaultListModel<Lobby> model;

    public LobbyListSection(ClientController clientController) {
        JLabel title = WidgetFactory.mediumLabel("Lobby selection");

        model = new DefaultListModel<>();
        lobbies = WidgetFactory.list(model, 15,
                this::formatLobbyID,
                this::formatPlayerCount,
                lobby -> new LobbyInfoCommand(clientController, lobby.getLobbyID()).execute());
        JScrollPane scrollPane = WidgetFactory.scrollPane(lobbies, Fonts.select);

        panel = new PanelBuilder()
                .border(title, scrollPane, null, null, null)
                .buildPanel();
    }

    @Override
    public void render(ClientController clientController) {;
        updateLobbyList(clientController);

        Lobby currLobby = clientController.getCurrLobby();
        lobbies.setEnabled(currLobby == null || !currLobby.containsClient(clientController.getID()));

        if (lobbies.getSelectedValue() != null && !lobbies.getSelectedValue().equals(clientController.getCurrLobby()))
            lobbies.clearSelection();
    }

    private void updateLobbyList(ClientController clientController) {
        Map<Integer, Lobby> waitingLobbies = clientController.getWaitingLobbies();
        for(Lobby lobby : waitingLobbies.values())
            if (!model.contains(lobby))
                model.addElement(lobby);

        int i = 0;
        while (i < model.size()) {
            if (waitingLobbies.get(model.get(i).getLobbyID()) == null)
                model.remove(i);
            else
                i++;
        }
    }

    private String formatLobbyID(Lobby lobby) {
        return String.format("• Lobby #%d", lobby.getLobbyID());
    }

    private String formatPlayerCount(Lobby lobby) {
        return String.format("Players: %d / %d   ", lobby.getPlayerCount(), lobby.getSize());
    }
}
