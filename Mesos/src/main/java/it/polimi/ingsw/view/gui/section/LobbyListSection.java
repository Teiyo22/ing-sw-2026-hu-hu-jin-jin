package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
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
        lobbies = WidgetFactory.createJList(model, Fonts.select,
                entry -> String.format("Lobby #%-3d | Player Count %3d/%3d ",
                    entry.getLobbyID(),
                    entry.getPlayerCount(), entry.getSize()));

        lobbies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lobbies.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Lobby selected = lobbies.getSelectedValue();

                if (selected != null) {
                    int id = selected.getLobbyID();
                    new LobbyInfoCommand(clientController, id).execute();
                }
            }
        });

        scrollPane = new JScrollPane(lobbies);
        scrollPane.setOpaque(false);

        panel = new PanelBuilder().border(title, scrollPane, null, null, null)
                .buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel panel) {
        model.clear();
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

        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby != null)
            lobbies.setEnabled(!currLobby.containsClient(clientController.getID()));
    }

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
