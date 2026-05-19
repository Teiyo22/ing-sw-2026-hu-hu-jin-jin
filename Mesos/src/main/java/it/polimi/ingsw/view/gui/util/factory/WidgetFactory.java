package it.polimi.ingsw.view.gui.util.factory;

import it.polimi.ingsw.view.command.Command;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
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

    public static JButton createButton(AbstractAction action, int width, int height) {
        JButton button = createButton(action);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    public static JLabel createLabel(String content) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setFont(Fonts.medium);
        label.setForeground(Color.WHITE);
        label.setOpaque(false);

        return label;
    }

    public static JLabel createLabel(String content, int width, int height) {
        JLabel label = createLabel(content);
        label.setPreferredSize(new Dimension(width, height));
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
        return textField;
    }

    public static JTextField createTextField(int width, int height) {
        JTextField textField = createTextField();
        textField.setMaximumSize(new Dimension(width, height));
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

    public static <E> JComboBox<E> createBox(E[] items, int width, int height) {
        JComboBox<E> box = createBox(items);
        box.setMaximumSize(new Dimension(width, height));
        return box;
    }

    public static <T> JList<T> createJList(
            DefaultListModel<T> model,
            Function<T, String> labelExtractor) {

        JList<T> jlist = new JList<>();
        jlist.setOpaque(false);
        jlist.setFont(Fonts.small);
        jlist.setModel(model);

        jlist.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = createLabel(labelExtractor.apply(value));
            JPanel cell = new PanelBuilder()
                    .border(null, null, null, label, null)
                    .withPadding(0, 5, 0, 5)
                    .buildPanel();

            cell.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            cell.setOpaque(false);
            return cell;
        });

        return jlist;
    }

    public static <T> JList<T> createJList(
            DefaultListModel<T> model,
            Color background,
            Function<T, String> labelExtractor) {

        JList<T> jlist = createJList(model, labelExtractor);
        jlist.setOpaque(true);
        jlist.setBackground(background);
        return jlist;
    }
}
