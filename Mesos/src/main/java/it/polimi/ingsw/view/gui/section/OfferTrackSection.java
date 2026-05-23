package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.components.OfferTileComponent;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class OfferTrackSection extends GUISection {
    private final OfferPickListener listener;

    public OfferTrackSection(ClientController controller, OfferPickListener listener, Map<Totem, Image> totemIcons) {
        this.listener = listener;

        OfferTile[] offerTrack = controller.getBoard().getOfferTrack();
        for (int i = 0; i < offerTrack.length; i++) {
            OfferTileComponent offerTileComponent = new OfferTileComponent(offerTrack[i], listener, i, totemIcons);
            listener.addComponent(offerTileComponent);
        }

        panel = new PanelBuilder().row(0, listener.getComponents().toArray(new OfferTileComponent[0])).buildPanel();
    }

    @Override
    public void render(ClientController controller) {
        for (OfferTileComponent c : listener.getComponents()) {
            c.update(controller);
        }

        listener.setEnabled(controller.getCurrLobby().getTurnState().canPickOffer() &&
                controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));
        listener.resetPick();
    }
}
