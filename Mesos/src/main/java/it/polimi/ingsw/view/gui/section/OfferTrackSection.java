package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.components.OfferTileComponent;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class OfferTrackSection implements GUISection {
    private final JPanel panel;

    private final OfferPickListener listener;

    public OfferTrackSection(ClientController controller, OfferPickListener listener) {
        this.listener = listener;

        panel = new PanelBuilder().flow().withColor(Fonts.other_red).buildPanel();

        for(OfferTile o : controller.getCurrLobby().getBoard().getOfferTrack()) {
            OfferTileComponent offerTileComponent = new OfferTileComponent(o, listener);
            listener.addCompoent(offerTileComponent);
            panel.add(offerTileComponent);
        }
    }

    @Override
    public void render(ClientController controller, JPanel container) {
        //TODO: render totems on top of offer tiles

        listener.setEnabled(controller.getCurrLobby().getTurnState().canPickOffer() &&
                controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));
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
