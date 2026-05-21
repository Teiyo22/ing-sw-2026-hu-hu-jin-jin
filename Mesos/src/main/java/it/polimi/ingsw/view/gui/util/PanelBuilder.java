package it.polimi.ingsw.view.gui.util;

import javax.swing.*;
import java.awt.*;

public class PanelBuilder {
    private JPanel panel;

    public PanelBuilder() {
        panel = new JPanel();
        panel.setVisible(true);
        panel.setOpaque(false);
    }

    public PanelBuilder column(int gap, JComponent... components) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(gap));
        for (JComponent c : components) {
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(c);
            panel.add(Box.createVerticalStrut(gap));
        }

        return this;
    }

    public PanelBuilder row(int gap, JComponent... components) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createHorizontalGlue());

        for (int i = 0; i < components.length; i++) {
            panel.add(components[i]);
            panel.add(Box.createHorizontalGlue());
            if (i < components.length - 1)
                panel.add(Box.createHorizontalStrut(gap));
        }

        return this;
    }

    public PanelBuilder grid(int cols, int hgap, int vgap, JComponent... components) {
        int rows = (int) Math.ceil((double) components.length / cols);
        panel.setLayout(new GridLayout(rows, cols, hgap, vgap));

        for (Component c : components)
            panel.add(c);

        return this;
    }

    public PanelBuilder border(JComponent north, JComponent center, JComponent south, JComponent west, JComponent east) {
        panel.setLayout(new BorderLayout());

        if (north != null) panel.add(north, BorderLayout.NORTH);
        if (center != null) panel.add(center, BorderLayout.CENTER);
        if (south != null) panel.add(south, BorderLayout.SOUTH);
        if (west != null) panel.add(west, BorderLayout.WEST);
        if (east != null) panel.add(east, BorderLayout.EAST);

        return this;
    }

    public PanelBuilder withColor(Color color) {
        panel.setOpaque(true);
        panel.setBackground(color);

        return this;
    }

    public PanelBuilder withTranslucentColor(Color color) {
        panel.setOpaque(false);
        panel.setBackground(color);

        return this;
    }

    public PanelBuilder withPadding(int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    public PanelBuilder rounded(int radius) {
        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2.dispose();
            }
        };

        roundedPanel.setVisible(true);
        panel = roundedPanel;
        return this;
    }

    public PanelBuilder centered() {
        panel.add(Box.createVerticalGlue(), 0);
        panel.add(Box.createVerticalGlue());
        return this;
    }

    public PanelBuilder size(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public PanelBuilder edit(JPanel panel) {
        this.panel = panel;
        return this;
    }

    public JPanel buildPanel() {
        return panel;
    }
}