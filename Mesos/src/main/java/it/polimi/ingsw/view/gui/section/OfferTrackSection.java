package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.gui.components.OfferTileComponent;
import it.polimi.ingsw.view.gui.components.SelectableComponent;
import it.polimi.ingsw.view.gui.components.SelectionListener;
import it.polimi.ingsw.view.gui.util.ImageCache;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class OfferTrackSection extends GUISection implements SelectionListener<SelectableComponent<OfferTile>> {
    private final List<SelectableComponent<OfferTile>> components;
    private SelectableComponent<OfferTile> selectedComponent;

    public OfferTrackSection(ClientController controller, ImageCache imageCache) {
        components = new ArrayList<>();
        selectedComponent = null;

        Board board = controller.getBoard();
        if (board != null)
            for (OfferTile offerTile : board.getOfferTrack())
                components.add(new OfferTileComponent(offerTile, this, imageCache));

        panel = new PanelBuilder()
            .row(5, components.toArray(new SelectableComponent[0]))
            .buildPanel();
        panel.setEnabled(false);
    }

    @Override
    public void render(ClientController controller) {
        Dimension parentSize = panel.getParent().getSize();
        panel.setPreferredSize(new Dimension(parentSize.width, parentSize.height / 2));

        Board board = controller.getBoard();
        Lobby currLobby = controller.getCurrLobby();

        if (currLobby == null || board == null)
            return;

        for (int i = 0; i < board.getOfferTrack().length; i++)
            components.get(i).render(board.getOfferTrack()[i]);

        if (currLobby.getTurnState() != null && currLobby.getTurnState().canPickOffer()) {
            panel.setEnabled(true);
        } else {
            panel.setEnabled(false);
            resetSelection();
        }
    }

    @Override
    public void onSelect(SelectableComponent<OfferTile> offerTileComponent) {
        if (!panel.isEnabled())
            return;

        if (selectedComponent != null)
            selectedComponent.setSelected(false);

        if (offerTileComponent != selectedComponent) {
            selectedComponent = offerTileComponent;
            selectedComponent.setSelected(true);
        } else {
            selectedComponent = null;
        }
    }

    @Override
    public void resetSelection() {
        if (selectedComponent != null) {
            selectedComponent.setSelected(false);
            selectedComponent = null;
        }
    }

    public int getSelectedOfferIndex() {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) == selectedComponent)
                return i;
        }

        return -1;
    }

    public List<SelectableComponent<OfferTile>> getComponents() {
        return components;
    }
}
