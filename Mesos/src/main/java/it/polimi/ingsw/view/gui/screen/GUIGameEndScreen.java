package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.view.ImageCache;
import it.polimi.ingsw.utils.view.WidgetFactory;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.action.GUILeaderboardAction;
import it.polimi.ingsw.view.gui.section.LeaderboardSection;
import it.polimi.ingsw.view.gui.section.RankingSection;

import java.awt.*;
import java.util.List;


public class GUIGameEndScreen extends GUIScreen {
    CardLayout cardLayout;

    public GUIGameEndScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);
        background = ImageCache.loadImage("/images/mesos_blurred.png");
        cardLayout = new CardLayout();

        sections = List.of(
            new RankingSection(clientController, WidgetFactory.mediumButton(new GUILeaderboardAction(clientController, this))),
            new LeaderboardSection(clientController, WidgetFactory.mediumButton("Ranking", this::showRanking))
        );

        this.setLayout(cardLayout);
        this.add(sections.get(0).getPanel(), "ranking");
        this.add(sections.get(1).getPanel(), "leaderboard");
    }

    public void showLeaderboard() {
        cardLayout.show(this, "leaderboard");
    }

    public void showRanking() {
        cardLayout.show(this, "ranking");
    }
}