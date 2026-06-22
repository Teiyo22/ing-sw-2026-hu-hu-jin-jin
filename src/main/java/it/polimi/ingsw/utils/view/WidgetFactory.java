package it.polimi.ingsw.utils.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class WidgetFactory {

    // ===============================================================
    // Button factory methods
    // ===============================================================

    private static JButton defaultButton(AbstractAction action) {
        JButton button = new JButton();

        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setAction(action);

        return button;
    }

    public static JButton mediumButton(AbstractAction action) {
        JButton button = defaultButton(action);

        button.setFont(Fonts.medium);
        button.setBorderPainted(false);

        return button;
    }

    public static JButton mediumButton(String name, Runnable action) {
        return mediumButton(new AbstractAction(name) {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    public static JButton mediumButton(AbstractAction action, int width, int height) {
        JButton button = mediumButton(action);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    public static JButton tinyButton(AbstractAction action) {
        JButton button = defaultButton(action);

        button.setFont(Fonts.tiny);
        button.setBorderPainted(true);

        return button;
    }

    public static JButton tinyButton(String name, Runnable action) {
        return tinyButton(new AbstractAction(name) {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    // ===============================================================
    // Label factory methods
    // ===============================================================

    private static JLabel defaultLabel(String content) {
        JLabel label = new JLabel(content, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(false);

        return label;
    }

    public static JLabel mediumLabel(String content) {
        JLabel label = defaultLabel(content);
        label.setFont(Fonts.medium);

        return label;
    }

    public static JLabel mediumLabel(String content, int width, int height) {
        JLabel label = mediumLabel(content);
        label.setPreferredSize(new Dimension(width, height));

        return label;
    }

    public static JLabel smallLabel(String content) {
        JLabel label = defaultLabel(content);
        label.setFont(Fonts.small);

        return label;
    }

    public static JLabel monospacedLabel(String content) {
        JLabel label = defaultLabel(content);
        label.setFont(Fonts.monospaced);

        return label;
    }

    public static JLabel messageLabel(String content) {
        JLabel label = defaultLabel(content);
        label.setForeground(Color.BLACK);
        label.setFont(Fonts.monospaced);

        return label;
    }

    public static JLabel createRankCell(String content, Color color) {
        JLabel label = defaultLabel(content);

        label.setBackground(color);
        label.setOpaque(true);

        return label;
    }

    public static JLabel createLeaderboardCell(String content, Color color) {
        JLabel label = defaultLabel(content);
        label.setFont(Fonts.small);

        label.setBackground(color);
        label.setOpaque(true);

        return label;
    }

    // ===============================================================
    // Other factory methods
    // ===============================================================

    public static JTextField textField() {
        JTextField textField = new JTextField();
        textField.setFont(Fonts.small);
        textField.setForeground(Color.WHITE);
        textField.setBackground(Fonts.menu);
        return textField;
    }

    public static JTextField textField(int width, int height) {
        JTextField textField = textField();
        textField.setMaximumSize(new Dimension(width, height));
        return textField;
    }

    public static <E> JComboBox<E> comboBox(E[] items) {
        JComboBox<E> box = new JComboBox<>(items);
        box.setFont(Fonts.small);
        box.setForeground(Color.WHITE);
        box.setBackground(Fonts.menu);

        return box;
    }

    public static <E> JComboBox<E> comboBox(E[] items, int width, int height) {
        JComboBox<E> box = comboBox(items);
        box.setMaximumSize(new Dimension(width, height));
        box.setPreferredSize(new Dimension(width, height));
        return box;
    }

    public static <T> JList<T> list(
            DefaultListModel<T> model, int padding,
            Function<T, String> labelExtractor1,
            Function<T, String> labelExtractor2,
            Consumer<T> action) {

        JList<T> jlist = new JList<>();
        jlist.setModel(model);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jlist.setFont(Fonts.small);
        jlist.setOpaque(false);

        jlist.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label1 = labelExtractor1 != null ? mediumLabel(labelExtractor1.apply(value)) : null;
            JLabel label2 = labelExtractor2 != null ? mediumLabel(labelExtractor2.apply(value)) : null;

            JPanel cell = new PanelBuilder()
                    .border(null, WidgetFactory.mediumLabel(""), null, label1, label2)
                    .withPadding(padding, padding, padding, padding)
                    .withTranslucentColor(Fonts.select)
                    .buildPanel();

            cell.setOpaque(isSelected);

            return cell;
        });

        if (action != null) {
            jlist.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    T selected = jlist.getSelectedValue();

                    if (selected != null)
                        action.accept(selected);
                }
            });
        } else
            jlist.setEnabled(false);

        return jlist;
    }

    public static JScrollPane scrollPane(JComponent component, Color color) {
        JScrollPane scrollPane = new JScrollPane(component,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        if(color != null) {
            scrollPane.setOpaque(true);
            scrollPane.setBackground(color);
        } else
            scrollPane.setOpaque(false);

        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        return  scrollPane;
    }

    public static JTabbedPane createTab(){
        JTabbedPane tab = new JTabbedPane();
        tab.setFont(Fonts.small.deriveFont(Font.BOLD));
        tab.setBackground(Color.BLACK);
        tab.setForeground(Color.WHITE);
        tab.setOpaque(false);

        tab.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPane, int itemIndex, int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? Color.BLACK : Color.DARK_GRAY);
                g.fillRect(x, y, w, h);
            }
        });
        
        return tab;
    }

    public static JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(0x888888));

        return separator;
    }
}
