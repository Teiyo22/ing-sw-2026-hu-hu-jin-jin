package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.screen.WaitingLobbiesScreen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends GUIScreen implements ActionListener {
    private JButton create;
    private JButton join;

    public MenuScreen(JFrame frame, ClientController clientController) {
        super(frame,clientController);
    }
    public void render(){
        JPanel panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon mesos = new ImageIcon(getClass().getResource("/mesos.png"));
                g.drawImage(mesos.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagConstraints c = new GridBagConstraints();

        create = new JButton("Create Game");
        create.setPreferredSize(new Dimension(300, 100));
        create.setFont(new Font("Arial", Font.PLAIN, 30));
        create.setBackground(new Color(0xFFF3D3));
        create.setBorder(BorderFactory.createEtchedBorder());
        create.setFocusable(false);
        create.addActionListener(this);
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        panel1.add(create, c);

        join = new JButton("Join Game");
        join.setPreferredSize(new Dimension(300, 100));
        join.setBackground(new Color(0xFFF3D3));
        join.setFont(new Font("Arial", Font.PLAIN, 30));
        join.setBorder(BorderFactory.createEtchedBorder());
        join.setFocusable(false);
        join.addActionListener(this);
        c.gridy = 1;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.CENTER;
        panel1.add(join, c);

        frame.setContentPane(panel1);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==create) {
            new CreateGameScreen(frame,clientController).render();
        }else if(e.getSource()==join){
            new WaitingLobbiesScreen(frame,clientController).render();
        }
    }
}
