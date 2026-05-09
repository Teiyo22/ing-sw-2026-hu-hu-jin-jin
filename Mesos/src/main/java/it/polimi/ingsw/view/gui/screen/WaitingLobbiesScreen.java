package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.view.gui.screen.LobbyInfoScreen;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class WaitingLobbiesScreen extends GUIScreen implements ActionListener {
    private JButton back;
    private JButton refresh;
    private JList<Lobby> availables;
    private JPanel panel;
    private JPanel createPanel;
    private JButton create;

    public WaitingLobbiesScreen(JFrame frame, ClientController clientController) {
        super(frame,clientController);
    }

    @Override
    public void render() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Fonts.black);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Fonts.black);

        back = new JButton("←");
        back.setFont(Fonts.medium);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);

        topBar.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Lobbies", SwingConstants.CENTER);
        title.setFont(Fonts.large);
        title.setForeground(Color.WHITE);

        topBar.add(title, BorderLayout.CENTER);
;
        refresh = new JButton("Refresh");
        refresh.setFont(Fonts.large);
        refresh.setBorderPainted(false);
        refresh.setContentAreaFilled(false);
        refresh.setForeground(Color.WHITE);
        refresh.addActionListener(this);

        topBar.add(refresh, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        availables = new JList<>();
        availables.setBackground(new Color(0));
        availables.setFont(Fonts.small);

        Map<Integer,Lobby> lobbies = clientController.getWaitingLobbies();
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
            cell.add(id, BorderLayout.WEST);
            cell.add(players, BorderLayout.EAST);
            if(isSelected) {
                cell.setBackground(list.getSelectionBackground());
            } else {
                cell.setBackground(list.getBackground());
            }
            cell.setOpaque(true);
            return cell;
        });


        JScrollPane scrollPane = new JScrollPane(availables);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth()/2, 0));
        panel.add(scrollPane, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(0));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Fonts.black);
        infoPanel.setPreferredSize(new Dimension(0, frame.getHeight()/2));
        rightPanel.add(infoPanel, BorderLayout.CENTER);

        createPanel = new JPanel(new GridBagLayout());
        createPanel.setBackground(new Color(0));
        createPanel.setPreferredSize(new Dimension(0, frame.getHeight()/2));

        create = new JButton("Create Game");
        create.setFont(Fonts.medium);
        create.setForeground(Color.WHITE);
        create.setContentAreaFilled(false);
        create.addActionListener(this);
        createPanel.add(create);

        rightPanel.add(createPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.CENTER);

        availables.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selezionato = availables.getSelectedValue().getLobbyID();
                infoPanel.removeAll();
                try {
                    clientController.getServer().getLobbyInfo(clientController.getID(), selezionato);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                new LobbyInfoScreen(frame, clientController, infoPanel, selezionato).render();
                infoPanel.revalidate();
                infoPanel.repaint();
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == back){
            new MenuScreen(frame,clientController).render();
        }else if(e.getSource() == refresh){
            panel.removeAll();
            render();
            panel.revalidate();
            panel.repaint();
        }else if(e.getSource()==create){
            createPanel.removeAll();
            new CreateGameScreen(frame,create,clientController,createPanel).render();
            createPanel.revalidate();
            createPanel.repaint();
        }
    }
}
