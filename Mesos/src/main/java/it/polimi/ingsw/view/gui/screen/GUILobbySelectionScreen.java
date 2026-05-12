package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.GetWaitingLobbiesCommand;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.CreateGameSection;
import it.polimi.ingsw.view.gui.section.GUISection;
import it.polimi.ingsw.view.gui.section.LobbyInfoSection;
import it.polimi.ingsw.view.gui.section.LobbyListSection;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUILobbySelectionScreen extends GUIScreen {
    private final JPanel main;
    private final JPanel lobbyListPanel;
    private final JPanel lobbyCreatePanel;
    private final JPanel lobbyInfoPanel;

    private final List<GUISection> sections = List.of(
            new LobbyListSection(clientController),
            new CreateGameSection(clientController),
            new LobbyInfoSection(clientController));

    public GUILobbySelectionScreen(GUIView frame, ClientController clientController) {
        super(frame,clientController);
        lobbyListPanel = sections.get(0).getPanel();
        lobbyCreatePanel = sections.get(1).getPanel();
        lobbyInfoPanel = sections.get(2).getPanel();

        JPanel lobbySelectionPanel = new PanelBuilder()
                .border(null, lobbyListPanel, lobbyCreatePanel, null, null)
                .buildPanel();


        main = new PanelBuilder()
                .grid(2, 0, 0, lobbySelectionPanel, lobbyInfoPanel)
                .withColor(Fonts.brown)
                .buildPanel();
    }

    @Override
    public void render() {
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

    }

    @Override
    public void showError(String error) {

    }
}
