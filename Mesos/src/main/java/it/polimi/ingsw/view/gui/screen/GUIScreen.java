package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.GUISection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class GUIScreen extends JPanel implements Screen {
    protected ClientController clientController;
    protected GUIView frame;

    protected String backgroundPath = "/images/mesos_blurred.png";
    protected List<GUISection> sections = List.of();

        public GUIScreen(GUIView frame, ClientController clientController) {
            this.frame = frame;
            this.clientController = clientController;
        }

    public void render() {
        sections.forEach(s -> s.render(clientController));

        frame.setContentPane(this);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon(getClass().getResource(backgroundPath));
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void showError(String error) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
}
