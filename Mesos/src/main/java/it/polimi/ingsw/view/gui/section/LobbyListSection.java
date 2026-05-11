package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LobbyListSection implements GUISection {

    @Override
    public void render(ClientController clientController, JPanel panel) {
        JList<Lobby> availables = new JList<>();
        availables.setBackground(Fonts.black);
        availables.setFont(Fonts.small);

        Map<Integer, Lobby> lobbies = clientController.getWaitingLobbies();
        DefaultListModel<Lobby> model = new DefaultListModel<>();
        for(Lobby lobby : lobbies.values())
            model.addElement(lobby);

        availables.setModel(model);

        availables.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel cell = new JPanel(new BorderLayout());
            JLabel id = new JLabel("Lobby #" + value.getLobbyID());
            JLabel players = new JLabel(value.getPlayers().size() + "/" + value.getSize());
            id.setFont(Fonts.medium);
            players.setFont(Fonts.medium);
            id.setForeground(Color.WHITE);
            players.setForeground(Color.WHITE);
            cell.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            cell.add(id, BorderLayout.WEST);
            cell.add(players, BorderLayout.EAST);
            cell.setOpaque(true);
            return cell;
        });

        JScrollPane scrollPane = new JScrollPane(availables);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth()/2, 0));
        panel.add(scrollPane, BorderLayout.WEST);

    }

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }
}
