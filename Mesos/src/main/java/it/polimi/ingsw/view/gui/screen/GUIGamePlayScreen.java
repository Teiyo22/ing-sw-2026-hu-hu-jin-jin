package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.section.*;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIGamePlayScreen extends GUIScreen{
    private JPanel topRowPanel;
    private JPanel bottomRowPanel;
    private JPanel offerTrackPanel;
    private JPanel gameInfoPanel;

    private final List<GUISection> sections;

    public GUIGamePlayScreen(GUIView frame, ClientController controller) {
        super(frame, controller);

        CardPicksListener topListener = new CardPicksListener();
        CardPicksListener bottomListener = new CardPicksListener();

        OfferPickListener offerPickListener = new OfferPickListener();

        Map<Totem, ImageIcon> totemIcons = new HashMap<>();
        for(Player p: controller.getCurrLobby().getPlayers().keySet()){
            Image img = new ImageIcon(getClass().getResource("/images/totems/"+p.getTotem()+".png")).getImage();
            totemIcons.put(p.getTotem(), new ImageIcon(img.getScaledInstance(58, 40, Image.SCALE_DEFAULT)));
        }

        sections = List.of(new TopRowSection(topListener),
                new BottomRowSection(bottomListener),
                new OfferTrackSection(controller, offerPickListener, totemIcons),
                new GameInfoSection(clientController, topListener, bottomListener, offerPickListener, totemIcons));

        topRowPanel = sections.get(0).getPanel();
        bottomRowPanel = sections.get(1).getPanel();
        offerTrackPanel = sections.get(2).getPanel();
        gameInfoPanel = sections.get(3).getPanel();

        JPanel board = new PanelBuilder().border(topRowPanel, offerTrackPanel, bottomRowPanel, null, null).buildPanel();

        this.setLayout(new BorderLayout());
        this.add(new PanelBuilder().column(0, gameInfoPanel).withPadding(30, 30, 30, 0).buildPanel()
                , BorderLayout.WEST);
        this.add(board, BorderLayout.CENTER);

    }

    @Override
    public void render() {
        sections.stream()
                .filter(s -> s.isVisible(clientController))
                .forEach(s -> s.render(clientController, this));

        frame.setContentPane(this);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showError(String error) {

    }
}
