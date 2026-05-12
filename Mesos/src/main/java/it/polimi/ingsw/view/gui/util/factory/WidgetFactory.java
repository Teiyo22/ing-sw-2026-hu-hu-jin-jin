package it.polimi.ingsw.view.gui.util.factory;

import it.polimi.ingsw.view.command.Command;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class WidgetFactory {
    public static JButton createButton(String label, Supplier<Command> supplier) {
        JButton button = new JButton(label);
        button.setFont(Fonts.medium);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(e -> supplier.get().execute());

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

    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setPreferredSize(new Dimension(200,30));

        return textField;
    }

    public static <E> JComboBox<E> createBox(E[] items) {
        JComboBox<E> box = new JComboBox<>(items);
        box.setFont(Fonts.small);
        box.setPreferredSize(new Dimension(200,30));

        return box;
    }
}
