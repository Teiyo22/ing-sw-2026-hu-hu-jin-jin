package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.view.gui.screen.LobbyInfoScreen;

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
    private JList<Integer> availables;
    private JPanel panel;

    public WaitingLobbiesScreen(JFrame frame, ClientController clientController) {
        super(frame,clientController);
    }

    @Override
    public void render() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFFF3D3));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0xFFF3D3));

        back = new JButton("←");
        back.setFont(new Font("Arial", Font.PLAIN, 30));
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(Color.BLACK);
        back.addActionListener(this);

        topBar.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Lobbies", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 40));

        topBar.add(title, BorderLayout.CENTER);

        ImageIcon r = new ImageIcon(getClass().getResource("/r.png"));
        refresh = new JButton(r);
        refresh.setFont(new Font("Arial", Font.PLAIN, 40));
        refresh.setBorderPainted(false);
        refresh.setContentAreaFilled(false);
        refresh.addActionListener(this);

        topBar.add(refresh, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        availables = new JList<>();
        availables.setBackground(new Color(0xFFF3D3));
        availables.setFont(new Font("Arial", Font.PLAIN, 20));

        Map<Integer,Lobby> lobbies = clientController.getWaitingLobbies();
        DefaultListModel<Integer> model = new DefaultListModel<>();
        for(Lobby lobby : lobbies.values())
            model.addElement(lobby.getLobbyID());

        availables.setModel(model);

        JScrollPane scrollPane = new JScrollPane(availables);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth()/2, 0));
        panel.add(scrollPane, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(0xFFF3D3));
        panel.add(infoPanel, BorderLayout.CENTER);

        availables.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selezionato = availables.getSelectedValue();
                infoPanel.removeAll();
                new LobbyInfoScreen(frame, clientController, infoPanel, selezionato).render();
                try {
                    clientController.getServer().getLobbyInfo(clientController.getID(), selezionato);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
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
        }
    }
}
