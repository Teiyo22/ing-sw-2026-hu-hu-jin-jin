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
    private final JPanel placeholder;
    private final JPanel lobbyListPanel;
    private final JPanel lobbyCreatePanel;
    private final JPanel lobbyInfoPanel;

    private final List<GUISection> sections;

    public GUILobbySelectionScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);

        placeholder = new JPanel(new CardLayout()) {
            @Override
            public Dimension getPreferredSize() {
                for (Component c : getComponents()) {
                    if (c.isVisible()) {
                        return c.getPreferredSize();
                    }
                }
                return super.getPreferredSize();
            }
        };

        sections = List.of(
                new LobbyListSection(clientController),
                new CreateGameSection(clientController, placeholder),
                new LobbyInfoSection(clientController));

        lobbyListPanel = sections.get(0).getPanel();
        lobbyCreatePanel = sections.get(1).getPanel();
        lobbyInfoPanel = sections.get(2).getPanel();

        placeholder.setMaximumSize(new Dimension(frame.getWidth() / 2, 300));

        JButton create = WidgetFactory.createButton(new AbstractAction("Create Lobby") {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) placeholder.getLayout()).show(placeholder, "form");
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(create);

        placeholder.add(buttonPanel, "button");
        placeholder.add(lobbyCreatePanel, "form");
        ((CardLayout) placeholder.getLayout()).show(placeholder, "button");

        JPanel lobbySelectionPanel = new PanelBuilder()
                .border(null, lobbyListPanel, placeholder, null, null)
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
                .forEach(s -> s.render(clientController, main));

        frame.setContentPane(main);
        frame.setVisible(true);
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
