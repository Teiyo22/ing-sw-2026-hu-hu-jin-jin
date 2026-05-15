package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.CreateGameSection;
import it.polimi.ingsw.view.gui.section.GUISection;
import it.polimi.ingsw.view.gui.section.LobbyInfoSection;
import it.polimi.ingsw.view.gui.section.LobbyListSection;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GUILobbySelectionScreen extends GUIScreen {
    private final JPanel main;
    private final JPanel lobbyListPanel;
    private final JPanel lobbyCreatePanel;
    private final JPanel lobbyInfoPanel;

    private final List<GUISection> sections;

    public GUILobbySelectionScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);

        sections = List.of(
                new LobbyListSection(clientController),
                new CreateGameSection(clientController),
                new LobbyInfoSection(clientController));

        lobbyListPanel = sections.get(0).getPanel();
        lobbyCreatePanel = sections.get(1).getPanel();
        lobbyInfoPanel = sections.get(2).getPanel();

        JPanel lobbySelectionPanel = new PanelBuilder()
                .border(null, lobbyListPanel, lobbyCreatePanel, null, null)
                .buildPanel();
        lobbySelectionPanel.setOpaque(false);

        main = new PanelBuilder()
                .grid(2, 20, 0, lobbySelectionPanel, lobbyInfoPanel)
                .withPadding(20,20,20,20)
                .buildPanel();

        this.setLayout(new BorderLayout());
        this.add(main,BorderLayout.CENTER);
    }

    @Override
    public void render() {
        sections.stream()
                .filter(s -> s.isVisible(clientController))
                .forEach(s -> s.render(clientController, main));

        frame.setContentPane(this);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void showError(String error) {

    }
}
