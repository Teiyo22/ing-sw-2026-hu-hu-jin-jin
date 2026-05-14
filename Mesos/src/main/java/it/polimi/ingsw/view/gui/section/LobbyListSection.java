package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;
import it.polimi.ingsw.view.gui.action.GUIGetWaitingLobbiesAction;
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

    private final JPanel topBar;

    public LobbyListSection(ClientController clientController) {
        title = WidgetFactory.createLabel("Lobby selection");

        topBar = new PanelBuilder().border(null, title, null, null,null).buildPanel();

        model = new DefaultListModel<>();
        lobbies = new JList<>();
        lobbies.setBackground(Fonts.cream);
        lobbies.setFont(Fonts.small);
        lobbies.setModel(model);
        lobbies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lobbies.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel cell = new JPanel(new BorderLayout());
            JLabel id = new JLabel(String.format("Lobby #%3d |  %3d/%3d ", value.getLobbyID(),value.getPlayerCount(), value.getSize()));
            id.setFont(Fonts.medium);
            id.setForeground(Color.WHITE);
            cell.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            cell.add(id, BorderLayout.WEST);
            cell.setOpaque(true);
            return cell;
        });

        lobbies.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Lobby selected = lobbies.getSelectedValue();

                if (selected != null) {
                    int id = selected.getLobbyID();
                    new LobbyInfoCommand(clientController, id).execute();
                } else {
                    System.out.println("No lobby selected.");
                }
            }
        });

        scrollPane = new JScrollPane(lobbies);

        panel = new PanelBuilder().border(topBar, scrollPane, null, null, null)
                .withColor(Fonts.weird_blue)
                .buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel panel) {
        Map<Integer, Lobby> lobbies = clientController.getWaitingLobbies();
        model.clear();
        for(Lobby lobby : lobbies.values())
            if (!model.contains(lobby))
                model.addElement(lobby);

        int i = 0;
        while (i < model.size()) {
            if (lobbies.get(model.get(i).getLobbyID()) == null)
                model.remove(i);
            else
                i++;
        }
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
