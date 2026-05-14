package it.polimi.ingsw.view.gui.util.factory;

import it.polimi.ingsw.view.command.Command;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
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

    public static JLabel createLabel(String content) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setFont(Fonts.medium);
        label.setForeground(Color.WHITE);
        label.setBackground(Fonts.brown);
        label.setOpaque(false);

        return label;
    }

    public static JLabel createImageLabel(String source, int width, int height) {
        Image image = new ImageIcon(source).getImage();
        return new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
    }

    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setPreferredSize(new Dimension(200, 30));

        return textField;
    }

    public static <E> JComboBox<E> createBox(E[] items) {
        JComboBox<E> box = new JComboBox<>(items);
        box.setFont(Fonts.small);
        box.setPreferredSize(new Dimension(200, 30));

        return box;
    }
}
