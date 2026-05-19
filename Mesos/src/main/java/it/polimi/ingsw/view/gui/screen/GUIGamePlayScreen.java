package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.section.*;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GUIGamePlayScreen extends GUIScreen{
    public GUIGamePlayScreen(GUIView frame, ClientController controller) {
        super(frame, controller);

        CardPicksListener topListener = new CardPicksListener();
        CardPicksListener bottomListener = new CardPicksListener();

        OfferPickListener offerPickListener = new OfferPickListener();

        sections = List.of(new TopRowSection(topListener),
                new BottomRowSection(bottomListener),
                new OfferTrackSection(controller, offerPickListener),
                new GameInfoSection(clientController, topListener, bottomListener, offerPickListener));

        JPanel topRowPanel = sections.get(0).getPanel();
        JPanel bottomRowPanel = sections.get(1).getPanel();
        JPanel offerTrackPanel = sections.get(2).getPanel();
        JPanel gameInfoPanel = sections.get(3).getPanel();

        JPanel board = new PanelBuilder()
                .border(topRowPanel, offerTrackPanel, bottomRowPanel, null, null)
                .buildPanel();

        JPanel gameInfoContainer = new PanelBuilder().column(0, gameInfoPanel)
                .withPadding(30, 30, 30, 0)
                .buildPanel();

        new PanelBuilder().edit(this)
                .border(null, board, null, gameInfoContainer, null);
    }
}
