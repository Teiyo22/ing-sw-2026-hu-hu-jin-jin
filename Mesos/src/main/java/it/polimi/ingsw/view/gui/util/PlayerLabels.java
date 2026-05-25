package it.polimi.ingsw.view.gui.util;

import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;

public class PlayerLabels {
    public JLabel name;
    public JLabel pp;
    public JLabel food;
    public JLabel fullSet;
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

    public PlayerLabels(String playerName){
        this.name               = WidgetFactory.createTabBoxLabel(playerName);
        this.pp                 = WidgetFactory.createTabBoxLabel("PP: 0");
        this.food               = WidgetFactory.createTabBoxLabel("Food: 0");
        this.fullSet            = WidgetFactory.createTabBoxLabel("Full Set: 0");
        this.sustenanceDiscount = WidgetFactory.createTabBoxLabel("Sustenance Discount: 0");
        this.stars              = WidgetFactory.createTabBoxLabel("Stars: 0");
        this.builderDiscount    = WidgetFactory.createTabBoxLabel("Builder Discount: 0");
        this.uniqueInventors       = WidgetFactory.createTabBoxLabel("Unique inventors: 0");
        this.collector          = WidgetFactory.createTabBoxLabel("Collectors: 0");
        this.hunter             = WidgetFactory.createTabBoxLabel("Hunters: 0");
        this.builder            = WidgetFactory.createTabBoxLabel("Builders: 0");
        this.shaman             = WidgetFactory.createTabBoxLabel("Shamans: 0");
        this.artist             = WidgetFactory.createTabBoxLabel("Artists: 0");
        this.inventor           = WidgetFactory.createTabBoxLabel("Inventors: 0");
    }
}
