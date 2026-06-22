package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.tui.TUIView;

public class ViewFactory {
    public static View create(String mode, ClientController controller) {
        return switch (mode) {
            case "gui" -> new GUIView(controller);
            case "tui" -> new TUIView(controller);
            default    -> throw new IllegalArgumentException("Unknown view type: " + mode);
        };
    }
}