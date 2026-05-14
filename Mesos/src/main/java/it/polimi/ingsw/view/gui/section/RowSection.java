package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class RowSection implements GUISection {
    private final JPanel panel;

    private final CardPicksListener listener;

    private final JPanel buildings;
    private final JPanel characters;
    private final JPanel events;

    public RowSection(CardPicksListener listener) {
        this.listener = listener;

        buildings = new PanelBuilder().flow().withColor(Fonts.other_red).buildPanel();
        characters = new PanelBuilder().flow().withColor(Fonts.light_brown).buildPanel();
        events = new PanelBuilder().flow().withColor(Fonts.other_red).buildPanel();

        panel = new PanelBuilder().grid(3, 0, 0, buildings, characters, events).withColor(Fonts.blue).buildPanel();
        panel.setPreferredSize(new Dimension(1920, 200));
    }

    public abstract List<AbstractBuilding> getBuildings(ClientController clientController);
    public abstract List<AbstractCharacter> getCharacters(ClientController clientController);
    public abstract List<AbstractEvent> getEvents(ClientController clientController);
    public abstract int getTotalPicks(ClientController clientController);

    @Override
    public void render(ClientController controller, JPanel container) {
        buildings.removeAll();
        characters.removeAll();
        events.removeAll();

        for (AbstractCard c : getBuildings(controller)) {
            renderCard(c, buildings, listener);
        }
        for (AbstractCard c : getCharacters(controller)) {
            renderCard(c, characters, listener);
        }
        for (AbstractCard c : getEvents(controller)) {
            renderCard(c, events, null);
        }

        listener.setEnabled(controller.getCurrLobby().getTurnState().canPickCard() &&
                controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));
        if(listener.isEnabled()) {
            listener.setTotalPicks(getTotalPicks(controller));
        }

        panel.revalidate();
        panel.repaint();
    }

    public void renderCard(AbstractCard c, JPanel panel, CardPicksListener listener) {
        CardComponent cardComponent = new CardComponent(c, listener);
        cardComponent.renderFront();
        panel.add(cardComponent);
    }

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
