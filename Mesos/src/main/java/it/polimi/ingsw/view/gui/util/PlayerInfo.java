package it.polimi.ingsw.view.gui.util;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;

public class PlayerInfo {
    private final Player player;

    public JLabel name;
    public JLabel pp;
    public JLabel food;
    public JLabel sustenanceDiscount;
    public JLabel stars;
    public JLabel builderDiscount;
    public JLabel uniqueInventors;
    public JLabel collector;
    public JLabel hunter;
    public JLabel builder;
    public JLabel shaman;
    public JLabel artist;
    public JLabel inventor;

    public PlayerInfo(Player player){
        this.player = player;

        this.name               = WidgetFactory.createTabBoxLabel(player.getName());
        this.pp                 = WidgetFactory.createTabBoxLabel("PP: 0");
        this.food               = WidgetFactory.createTabBoxLabel("Food: 0");
        this.sustenanceDiscount = WidgetFactory.createTabBoxLabel("Sustenance Discount: 0");
        this.stars              = WidgetFactory.createTabBoxLabel("Stars: 0");
        this.builderDiscount    = WidgetFactory.createTabBoxLabel("Builder Discount: 0");
        this.uniqueInventors    = WidgetFactory.createTabBoxLabel("Unique inventors: 0");
        this.collector          = WidgetFactory.createTabBoxLabel("Collectors: 0");
        this.hunter             = WidgetFactory.createTabBoxLabel("Hunters: 0");
        this.builder            = WidgetFactory.createTabBoxLabel("Builders: 0");
        this.shaman             = WidgetFactory.createTabBoxLabel("Shamans: 0");
        this.artist             = WidgetFactory.createTabBoxLabel("Artists: 0");
        this.inventor           = WidgetFactory.createTabBoxLabel("Inventors: 0");
    }

    public void renderLabels() {
        pp.setText("PP: " + player.getPP());
        food.setText("Food: " + player.getFood());
        sustenanceDiscount.setText("Sustenance Discount: " + player.getTribe().getSustenanceDiscount());
        stars.setText("Stars: " + player.getTribe().getStars());
        builderDiscount.setText("Builder Discount: " + player.getTribe().getBuilderDiscount());
        uniqueInventors.setText("Unique inventors: " + player.getTribe().getUniqueInventorsCount());
        collector.setText("Collectors: " + player.getTribe().getCollectorCount());
        hunter.setText("Hunters: " + player.getTribe().getHunterCount());
        builder.setText("Builders: " + player.getTribe().getBuilderCount());
        shaman.setText("Shamans: " + player.getTribe().getShamanCount());
        artist.setText("Artists: " + player.getTribe().getArtistCount());
        inventor.setText("Inventors: " + player.getTribe().getInventorCount());
    }

    public JPanel[] getRows() {
        JPanel playerInfo = createInfoPanel(name, pp, food);
        JPanel collectorInfo = createInfoPanel(collector, sustenanceDiscount);
        JPanel hunterInfo = createInfoPanel(hunter);
        JPanel builderInfo = createInfoPanel(builder, builderDiscount);
        JPanel shamanInfo = createInfoPanel(shaman, stars);
        JPanel artistInfo = createInfoPanel(artist);
        JPanel inventorInfo = createInfoPanel(inventor, uniqueInventors);

        return new JPanel[]{playerInfo, collectorInfo, hunterInfo, builderInfo, shamanInfo, artistInfo, inventorInfo};
    }

    private JPanel createInfoPanel(JLabel... labels) {
        return new PanelBuilder().rounded(20)
            .column(10, labels)
            .centered()
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(10, 10, 10, 10)
            .buildPanel();
    }
}
