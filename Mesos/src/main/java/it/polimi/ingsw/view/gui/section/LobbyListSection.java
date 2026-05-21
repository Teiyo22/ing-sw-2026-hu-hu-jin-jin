package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.util.Map;

public class LobbyListSection implements GUISection {
    private final JPanel panel;

    private final JLabel title;
    private final JScrollPane scrollPane;
    private final JList<Lobby> lobbies;
    private final DefaultListModel<Lobby> model;

    public LobbyListSection(ClientController clientController) {
        title = WidgetFactory.createLabel("Lobby selection");

        model = new DefaultListModel<>();
        lobbies = WidgetFactory.createJList(model,
                this::formatLobby,
                lobby -> new LobbyInfoCommand(clientController, lobby.getLobbyID()).execute());
        scrollPane = WidgetFactory.createScrollPane(lobbies, Fonts.select);

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

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
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

    private String formatLobby(Lobby lobby) {
        return String.format("Lobby #%d   •   Players: [ %d / %d ]",
                lobby.getLobbyID(), lobby.getPlayerCount(), lobby.getSize());
    }
}
