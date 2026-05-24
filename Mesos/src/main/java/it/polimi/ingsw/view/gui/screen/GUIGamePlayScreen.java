package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.*;
import it.polimi.ingsw.view.gui.util.CardCache;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIGamePlayScreen extends GUIScreen {
    public GUIGamePlayScreen(GUIView frame, ClientController controller) {
        super(frame, controller);

        CardCache cardCache = new CardCache();

        Map<Totem, Image> totemIcons = new HashMap<>();
        for (Player p : controller.getCurrLobby().getPlayers().keySet()) {
            totemIcons.put(p.getTotem(),
                    new ImageIcon(getClass().getResource("/images/totems/" + p.getTotem() + ".png")).getImage());
        }

        RowSection topRowSection = new TopRowSection(cardCache);
        RowSection bottomRowSection = new BottomRowSection(cardCache);
        OfferTrackSection offerTrackSection = new OfferTrackSection(controller, totemIcons);

        sections = List.of(
                topRowSection,
                bottomRowSection,
                offerTrackSection,
                new GameInfoSection(clientController, topRowSection, bottomRowSection, offerTrackSection, totemIcons)
        );

        JPanel topRowPanel = sections.get(0).getPanel();
        JPanel bottomRowPanel = sections.get(1).getPanel();
        JPanel offerTrackPanel = sections.get(2).getPanel();
        JPanel gameInfoPanel = sections.get(3).getPanel();

        JPanel board = new PanelBuilder()
                .border(topRowPanel, offerTrackPanel, bottomRowPanel, null, null)
                .withPadding(30, 30, 30, 30)
                .buildPanel();

        JPanel gameInfoContainer = new PanelBuilder().column(0, gameInfoPanel)
                .withPadding(30, 30, 30, 0)
                .buildPanel();

        new PanelBuilder().edit(this)
                .border(null, board, null, gameInfoContainer, null);
    }
}
