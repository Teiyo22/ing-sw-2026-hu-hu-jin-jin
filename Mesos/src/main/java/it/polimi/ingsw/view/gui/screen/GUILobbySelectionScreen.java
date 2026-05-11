package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.GetWaitingLobbiesCommand;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.CreateGameSection;
import it.polimi.ingsw.view.gui.section.GUISection;
import it.polimi.ingsw.view.gui.section.LobbyInfoSection;
import it.polimi.ingsw.view.gui.section.LobbyListSection;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class GUILobbySelectionScreen extends GUIScreen implements ActionListener {
    private final CreateGameSection createGameSection = new CreateGameSection();
    private final List<GUISection> sections = List.of(new LobbyInfoSection(), new LobbyListSection(),createGameSection);
    private JButton back;
    private JButton refresh;
    private JButton create;


    public GUILobbySelectionScreen(GUIView frame, ClientController clientController) {
        super(frame,clientController);
    }

    @Override
    public void render() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Fonts.black);

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

        refresh = new JButton("Refresh");
        refresh.setFont(Fonts.large);
        refresh.setBorderPainted(false);
        refresh.setContentAreaFilled(false);
        refresh.setForeground(Color.WHITE);
        refresh.addActionListener(this);

        topBar.add(refresh, BorderLayout.EAST);

        main.add(topBar, BorderLayout.NORTH);

        JPanel createPanel = new JPanel(new GridBagLayout());
        createPanel.setBackground(new Color(0));
        createPanel.setPreferredSize(new Dimension(0, frame.getHeight()/2));

        create = new JButton("Create Game");
        create.setFont(Fonts.medium);
        create.setForeground(Color.WHITE);
        create.setContentAreaFilled(false);
        create.addActionListener(this);
        createPanel.add(create);

        main.add(createPanel, BorderLayout.SOUTH);

        sections.stream()
                .filter(s -> s.isVisible(clientController))
                .forEach(s -> s.render(clientController,main));

        frame.setContentPane(main);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == back){
            new GUIMenuScreen(frame,clientController).render();
        }else if(e.getSource() == refresh){
            new GetWaitingLobbiesCommand().execute(clientController);
        }else if(e.getSource()==create){
            createGameSection.setVisible(true);
            render();
        }
    }

    @Override
    public void showError(String error) {

    }
}
