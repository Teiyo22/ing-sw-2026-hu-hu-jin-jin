package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.util.CardCache;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class RowSection extends GUISection {
    private final JPanel panel;

    private final CardPicksListener listener;
    private final CardCache cardCache;

    private final JPanel buildings;
    private final JPanel characters;
    private final JPanel events;

    public RowSection(CardPicksListener listener, CardCache cardCache) {
        this.listener = listener;
        this.cardCache = cardCache;

        buildings = new PanelBuilder().row(0).buildPanel();
        characters = new PanelBuilder().row(0).buildPanel();
        events = new PanelBuilder().row(0).buildPanel();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.height * 0.3);
        int width = screenSize.width;

        panel = new PanelBuilder()
                .row(0, buildings, characters, events)
                .size(width, height)
                .buildPanel();
    }

    public abstract List<AbstractBuilding> getBuildings(Board board);
    public abstract List<AbstractCharacter> getCharacters(Board board);
    public abstract List<AbstractEvent> getEvents(Board board);
    public abstract int getTotalPicks(ClientController clientController);

    @Override
    public void render(ClientController controller) {
        Lobby currLobby = controller.getCurrLobby();
        Board board = controller.getBoard();

        if (currLobby == null || board == null)
            return;

        buildings.removeAll();
        characters.removeAll();
        events.removeAll();

        for (AbstractCard c : getBuildings(board))
            renderCard(c, buildings, listener);

        for (AbstractCard c : getCharacters(board))
            renderCard(c, characters, listener);

        for (AbstractCard c : getEvents(board))
            renderCard(c, events, null);

        listener.setEnabled(currLobby.getTurnState() != null && currLobby.getTurnState().canPickCard());
        if(listener.isEnabled())
            listener.setTotalPicks(getTotalPicks(controller));
        else
            listener.resetPicks();
    }

    public void renderCard(AbstractCard c, JPanel panel, CardPicksListener listener) {
        CardComponent cardComponent = new CardComponent(c, listener, cardCache);
        panel.add(cardComponent);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
