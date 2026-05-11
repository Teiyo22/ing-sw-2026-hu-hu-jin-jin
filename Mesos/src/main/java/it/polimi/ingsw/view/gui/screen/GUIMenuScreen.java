package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIMenuScreen extends GUIScreen{

    public GUIMenuScreen(JFrame frame, ClientController clientController) {
        super(frame,clientController);
    }

    @Override
    public void render() {
        JPanel panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(getClass().getResource("/mesos.png"));
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        GridBagConstraints c = new GridBagConstraints();
        JLabel text = new JLabel("Press to enter");
        text.setFont(new Font("Arial",Font.PLAIN,30));
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets(300, 0, 50, 0);
        panel1.add(text, c);

        Timer timer = getTimer(text);

        panel1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                new GUILobbySelectionScreen(frame,clientController).render();
            }
        });

        frame.setContentPane(panel1);
        frame.setVisible(true);
    }

    private Timer getTimer(JLabel text) {
        float[] alpha = {0f};
        boolean[] aumenta = {true};

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(aumenta[0]) {
                    alpha[0] += 0.05f;
                    if(alpha[0] >= 1f) aumenta[0] = false;
                } else {
                    alpha[0] -= 0.05f;
                    if(alpha[0] <= 0f) aumenta[0] = true;
                }
                text.setForeground(new Color(1f, 1f, 1f, alpha[0]));
            }
        });
        timer.start();
        return timer;
    }
}



