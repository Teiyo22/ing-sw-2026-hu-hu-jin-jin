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
    public GUILobbySelectionScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);

        sections = List.of(
                new LobbyListSection(clientController),
                new CreateGameSection(clientController),
                new LobbyInfoSection(clientController));

        JPanel lobbyListPanel = sections.get(0).getPanel();
        JPanel lobbyCreatePanel = sections.get(1).getPanel();
        JPanel lobbyInfoPanel = sections.get(2).getPanel();

        JPanel lobbySelectionPanel = new PanelBuilder()
                .border(null, lobbyListPanel, lobbyCreatePanel, null, null)
                .buildPanel();

        new PanelBuilder().edit(this)
                .grid(2, 20, 0, lobbySelectionPanel, lobbyInfoPanel)
                .withPadding(20,20,20,20);

    }
}
