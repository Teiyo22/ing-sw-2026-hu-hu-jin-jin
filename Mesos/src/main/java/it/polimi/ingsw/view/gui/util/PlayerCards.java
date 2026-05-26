package it.polimi.ingsw.view.gui.util;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.components.CardComponent;

import javax.swing.*;
import java.util.List;
import java.util.stream.IntStream;

public class PlayerCards {
    private final ImageCache cache;
    private final Player player;

    private JPanel buildingRow;
    private JPanel collectorRow;
    private JPanel hunterRow;
    private JPanel builderRow;
    private JPanel shamanRow;
    private JPanel artistRow;
    private JPanel inventorRow;

    public PlayerCards(Player player, ImageCache cache) {
        this.player = player;
        this.cache = cache;

        buildingRow = new PanelBuilder().row(0).buildPanel();
        collectorRow = new PanelBuilder().row(0).buildPanel();
        hunterRow = new PanelBuilder().row(0).buildPanel();
        builderRow = new PanelBuilder().row(0).buildPanel();
        shamanRow = new PanelBuilder().row(0).buildPanel();
        artistRow = new PanelBuilder().row(0).buildPanel();
        inventorRow = new PanelBuilder().row(0).buildPanel();
    }

    public void renderCards() {
        renderRow(buildingRow, player.getBuildings());
        renderRow(collectorRow, player.getTribe().getCollectors());
        renderRow(hunterRow, player.getTribe().getHunters());
        renderRow(builderRow, player.getTribe().getBuilders());
        renderRow(shamanRow, player.getTribe().getShamans());
        renderRow(artistRow, player.getTribe().getArtists());
        renderRow(inventorRow, player.getTribe().getInventors());
    }

    private void renderRow(JPanel row, List<? extends AbstractCard> cards) {
        row.removeAll();
        for(AbstractCard card : cards) {
            CardComponent component = new CardComponent(null, cache, 0);
            row.add(component);
            component.render(card);
        }
    }

    public JPanel[] getRows() {
        return new JPanel[]{buildingRow, collectorRow, hunterRow, builderRow, shamanRow, artistRow, inventorRow};
    }
}
