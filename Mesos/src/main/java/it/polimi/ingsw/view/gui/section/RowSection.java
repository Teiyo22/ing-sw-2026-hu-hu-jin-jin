package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.SelectableComponent;
import it.polimi.ingsw.view.gui.components.SelectionListener;
import it.polimi.ingsw.view.gui.util.CardCache;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public abstract class RowSection extends GUISection implements SelectionListener<SelectableComponent<AbstractCard>> {
    private final Set<Integer> picks;

    private final List<CardComponent> buildings;
    private final List<CardComponent> characters;
    private final List<CardComponent> events;

    public abstract List<AbstractCard> getBuildings(Board board);

    public abstract List<AbstractCard> getCharacters(Board board);

    public abstract List<AbstractCard> getEvents(Board board);

    public RowSection(CardCache cardCache) {
        picks = new HashSet<>();

        events = IntStream.range(0, 4).mapToObj(i -> new CardComponent(null, cardCache)).toList();
        characters = IntStream.range(0, 9).mapToObj(i -> new CardComponent(this, cardCache)).toList();
        buildings = IntStream.range(0, 5).mapToObj(i -> new CardComponent(this, cardCache)).toList();

        JPanel eventsPanel = new PanelBuilder().rounded(20)
            .row(0, events.toArray(new CardComponent[0]))
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(10, 20, 10, 20)
            .buildPanel();

        JPanel charactersPanel = new PanelBuilder().rounded(20)
            .row(0, characters.toArray(new CardComponent[0]))
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(10, 20, 10, 20)
            .buildPanel();

        JPanel buildingsPanel = new PanelBuilder().rounded(20)
            .row(0, buildings.toArray(new CardComponent[0]))
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(10, 20, 10, 20)
            .buildPanel();

        panel = new PanelBuilder()
            .row(10, eventsPanel, charactersPanel, buildingsPanel)
            .buildPanel();

        panel.setEnabled(false);
    }

    @Override
    public void render(ClientController controller) {
        Lobby currLobby = controller.getCurrLobby();
        Board board = controller.getBoard();

        if (currLobby == null || board == null)
            return;

        renderCards(getBuildings(board), buildings);
        renderCards(getCharacters(board), characters);
        renderCards(getEvents(board), events);

        if (currLobby.getTurnState() != null && currLobby.getTurnState().canPickCard()) {
            panel.setEnabled(true);
        } else {
            panel.setEnabled(false);
            resetSelection();
        }
    }

    public void renderCards(List<AbstractCard> cards, List<CardComponent> components) {
        if (!cards.isEmpty()) {
            components.getFirst().getParent().setVisible(true);
            for (int i = 0; i < components.size(); i++) {
                AbstractCard card = i < cards.size() ? cards.get(i) : null;
                components.get(i).render(card);
            }
        } else {
            components.getFirst().getParent().setVisible(false);
        }
    }

    public Set<Integer> getPicks() {
        return picks;
    }

    @Override
    public void onSelect(SelectableComponent<AbstractCard> cardComponent) {
        if (!panel.isEnabled())
            return;

        int id = cardComponent.getElement().getID();
        if (picks.contains(id)) {
            picks.remove(id);
            cardComponent.setSelected(false);
        } else {
            picks.add(id);
            cardComponent.setSelected(true);
        }
    }

    @Override
    public void resetSelection() {
        picks.clear();

        for (CardComponent cardComponent : buildings)
            cardComponent.setSelected(false);

        for (CardComponent cardComponent : characters)
            cardComponent.setSelected(false);

        for (CardComponent cardComponent : events)
            cardComponent.setSelected(false);
    }
}
