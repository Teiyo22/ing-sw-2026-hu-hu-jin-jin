package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.view.gui.components.CardComponent;
import it.polimi.ingsw.view.gui.components.SelectableComponent;
import it.polimi.ingsw.view.gui.components.SelectionListener;
import it.polimi.ingsw.utils.view.ImageCache;
import it.polimi.ingsw.utils.view.Fonts;
import it.polimi.ingsw.utils.view.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public abstract class RowSection extends GUISection implements SelectionListener<SelectableComponent<AbstractCard>> {
    private final Set<Integer> picks;
    private final List<CardComponent> buildings;
    private final List<CardComponent> characters;
    private final List<CardComponent> events;

    public abstract List<AbstractBuilding> getBuildings(Board board);
    public abstract List<AbstractCharacter> getCharacters(Board board);
    public abstract List<AbstractEvent> getEvents(Board board);

    public RowSection(ImageCache imageCache, int maxCardCount) {
        picks = new HashSet<>();

        events = IntStream.range(0, 4).mapToObj(i -> new CardComponent(null, imageCache, maxCardCount)).toList();
        characters = IntStream.range(0, 9).mapToObj(i -> new CardComponent(this, imageCache, maxCardCount)).toList();
        buildings = IntStream.range(0, 5).mapToObj(i -> new CardComponent(this, imageCache, maxCardCount)).toList();

        JPanel eventsPanel = createCardPanel(events);
        JPanel charactersPanel = createCardPanel(characters);
        JPanel buildingsPanel = createCardPanel(buildings);

        panel = new PanelBuilder()
            .row(10, eventsPanel, charactersPanel, buildingsPanel)
            .buildPanel();

        panel.setEnabled(false);
    }

    @Override
    public void render(ClientController controller) {
        Dimension parentSize = panel.getParent().getSize();
        panel.setPreferredSize(new Dimension(parentSize.width, parentSize.height / 4));

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

    public void renderCards(List<? extends AbstractCard> cards, List<CardComponent> components) {
        if (!cards.isEmpty()) {
            for (int i = 0; i < components.size(); i++) {
                AbstractCard card = i < cards.size() ? cards.get(i) : null;
                components.get(i).render(card);
            }

            components.getFirst().getParent().setVisible(true);
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

    private JPanel createCardPanel(List<CardComponent> cards) {
        return new PanelBuilder().rounded(20)
            .row(0, cards.toArray(new CardComponent[0]))
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(10, 10, 10, 10)
            .buildPanel();
    }
}
