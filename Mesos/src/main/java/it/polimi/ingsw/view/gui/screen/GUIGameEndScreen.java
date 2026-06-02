package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.view.ImageCache;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.action.GUILeaderboardAction;
import it.polimi.ingsw.view.gui.section.LeaderboardSection;
import it.polimi.ingsw.view.gui.section.RankingSection;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class GUIGameEndScreen extends GUIScreen {
    public GUIGameEndScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);
        background = ImageCache.loadImage("/images/mesos_blurred.png");

        JButton leaderboardBtn = WidgetFactory.mediumButton(new GUILeaderboardAction(clientController, this));
        JButton rankingBtn = WidgetFactory.mediumButton("Ranking", this::showRanking);

        sections = List.of(
            new RankingSection(clientController, leaderboardBtn),
            new LeaderboardSection(clientController, rankingBtn)
        );

       new PanelBuilder().edit(this)
           .row(0, sections.get(0).getPanel(), sections.get(1).getPanel());

       showRanking();
    }

    public void showLeaderboard() {
        sections.get(1).getPanel().setVisible(true);
        sections.get(0).getPanel().setVisible(false);
    }

    public void showRanking() {
        sections.get(1).getPanel().setVisible(false);
        sections.get(0).getPanel().setVisible(true);
    }
}