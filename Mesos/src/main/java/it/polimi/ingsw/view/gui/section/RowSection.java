package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
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

        buildings = new PanelBuilder().row(0).buildPanel();
        characters = new PanelBuilder().row(0).buildPanel();
        events = new PanelBuilder().row(0).buildPanel();

        panel = new PanelBuilder().row(0, buildings, characters, events).buildPanel();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = (int) (screenSize.height*0.3);
        int width = screenSize.width;

        panel.setPreferredSize(new Dimension(width, height));
    }

    public abstract List<AbstractBuilding> getBuildings(ClientController clientController);
    public abstract List<AbstractCharacter> getCharacters(ClientController clientController);
    public abstract List<AbstractEvent> getEvents(ClientController clientController);
    public abstract int getTotalPicks(ClientController clientController);

    @Override
    public void render(ClientController controller) {
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
            listener.resetPicks();
            listener.setTotalPicks(getTotalPicks(controller));
        }

        panel.revalidate();
        panel.repaint();
    }

    public void renderCard(AbstractCard c, JPanel panel, CardPicksListener listener) {
        CardComponent cardComponent = new CardComponent(c, listener);
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
