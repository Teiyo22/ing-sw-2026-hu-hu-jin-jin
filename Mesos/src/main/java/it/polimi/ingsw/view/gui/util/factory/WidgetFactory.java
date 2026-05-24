package it.polimi.ingsw.view.gui.util.factory;

import it.polimi.ingsw.view.command.Command;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.function.Supplier;

public class WidgetFactory {
    public static JButton createButton(AbstractAction action) {
        JButton button = new JButton();
        button.setFont(Fonts.medium);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setAction(action);

        return button;
    }

    public static JLabel createRankCell(String content, Color color) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setFont(Fonts.medium);
        label.setForeground(Color.WHITE);
        label.setBackground(color);
        label.setPreferredSize(new Dimension(0, 60));
        label.setBorder(new EmptyBorder(0, 10, 0, 10));
        label.setOpaque(true);

        return label;
    }

    public static JLabel createLeaderboardCell(String content, Color color) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setFont(Fonts.small);
        label.setForeground(Color.WHITE);
        label.setBackground(color);
        label.setPreferredSize(new Dimension(0, 60));
        label.setBorder(new EmptyBorder(0, 10, 0, 10));
        label.setOpaque(true);

        return label;
    }

    public static JLabel createLabel(String content) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setFont(Fonts.medium);
        label.setForeground(Color.WHITE);
        label.setOpaque(false);

        return label;
    }

    public static JLabel createImageLabel(URL source, int width, int height) {
        Image image = new ImageIcon(source).getImage();
        JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(Fonts.small);
        textField.setForeground(Color.WHITE);
        textField.setBackground(Fonts.menu);
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    public static <E> JComboBox<E> createBox(E[] items) {
        JComboBox<E> box = new JComboBox<>(items);
        box.setFont(Fonts.small);
        box.setForeground(Color.WHITE);
        box.setBackground(Fonts.menu);
        box.setPreferredSize(new Dimension(200, 30));

        return box;
    }
}
