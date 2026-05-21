package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.components.OfferTileComponent;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;

public class OfferTrackSection implements GUISection {
    private final JPanel panel;

    private final OfferPickListener listener;

    public OfferTrackSection(ClientController controller, OfferPickListener listener) {
        this.listener = listener;

        OfferTile[] offerTrack = controller.getCurrLobby().getBoard().getOfferTrack();
        for(int i = 0; i<offerTrack.length; i++) {
            OfferTileComponent offerTileComponent = new OfferTileComponent(offerTrack[i], listener, i);
            listener.addComponent(offerTileComponent);
        }

        panel = new PanelBuilder().row(0, listener.getComponents().toArray(new OfferTileComponent[0])).buildPanel();
    }

    @Override
    public void render(ClientController controller) {
        //TODO: render totems on top of offer tiles

        for (OfferTileComponent c : listener.getComponents()) {
            c.update(controller);
        }

        listener.setEnabled(controller.getCurrLobby().getTurnState().canPickOffer() &&
                controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));
        listener.resetPick();
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
