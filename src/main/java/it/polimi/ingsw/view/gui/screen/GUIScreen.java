package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.components.ErrorPanel;
import it.polimi.ingsw.view.gui.section.GUISection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class GUIScreen extends JPanel implements Screen {
    protected ClientController clientController;
    protected GUIView frame;
    protected Image background;

    protected List<GUISection> sections = List.of();

        public GUIScreen(GUIView frame, ClientController clientController) {
            this.frame = frame;
            this.clientController = clientController;
        }

    public void render() {
        sections.forEach(s -> s.render(clientController));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void showErrors(ErrorMessage errorMsg) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                new ErrorPanel(errorMsg.getErrors()),
                errorMsg.getContext(),
                JOptionPane.PLAIN_MESSAGE);
        });
    }

    @Override
    public void showEventResult(EventResultMessage eventResultMessage) {}
}
