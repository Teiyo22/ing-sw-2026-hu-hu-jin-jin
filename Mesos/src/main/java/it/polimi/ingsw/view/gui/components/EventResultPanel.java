package it.polimi.ingsw.view.gui.components;

import it.polimi.ingsw.model.card.event.EventResult;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;

import javax.swing.*;
import java.util.List;
import java.util.stream.Stream;

public class EventResultPanel extends JPanel {

    public EventResultPanel(List<EventResult> results) {
        JLabel[] content = Stream.concat(
            createHeader().stream(),
            results.stream().flatMap(result -> createResultRow(result).stream())
        ).toArray(JLabel[]::new);

        new PanelBuilder().edit(this)
            .grid(3, 0, 0, content);
    }

    private List<JLabel> createHeader() {
        JLabel playerLabel = WidgetFactory.messageLabel("Player");
        JLabel foodDeltaLabel = WidgetFactory.messageLabel("Food Delta");
        JLabel ppDeltaLabel = WidgetFactory.messageLabel("PP Delta");
        return List.of(playerLabel, foodDeltaLabel, ppDeltaLabel);
    }

    private List<JLabel> createResultRow(EventResult result) {
        JLabel playerLabel = WidgetFactory.messageLabel(result.getPlayer().getName());
        JLabel foodDeltaLabel = WidgetFactory.messageLabel(String.valueOf(result.getFoodDelta()));
        JLabel ppDeltaLabel = WidgetFactory.messageLabel(String.valueOf(result.getPPDelta()));
        return List.of(playerLabel, foodDeltaLabel, ppDeltaLabel);
    }
}
