package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;

import javax.swing.*;
import java.util.List;

public class ErrorPanel extends JPanel {
    public ErrorPanel(List<String> errors) {
        new PanelBuilder().edit(this)
            .border(null, createErrorList(errors), null, null, null);
    }

    private JPanel createErrorList(List<String> errors) {
        JPanel listPanel = new PanelBuilder()
            .column(5, createErrorRows(errors))
            .buildPanel();

        return listPanel;
    }

    private JLabel[] createErrorRows(List<String> errors) {
        return errors.stream()
            .map(WidgetFactory::errorLabel)
            .toArray(JLabel[]::new);
    }
}
