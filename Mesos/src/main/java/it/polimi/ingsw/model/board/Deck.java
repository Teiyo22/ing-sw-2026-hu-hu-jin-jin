package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;

import java.util.*;

public class Deck {
    final private int gameSize;
    private final Board board;

    private final Stack<AbstractCard> charEventCards;
    private final Queue<AbstractBuilding> buildingCards;

    private final List<Integer> ageBuildingsCount;
    private int currentAge = 0;


    public Deck(int gameSize, Board board) {
        this.gameSize = gameSize;
        this.board = board;
        this.charEventCards = new Stack<AbstractCard>();
        this.buildingCards = new LinkedList<AbstractBuilding>();
        this.ageBuildingsCount = new ArrayList<>();
    }

    public void init() {

    }

    public List<AbstractCard> drawCards(int num) {

    }

    public List<AbstractBuilding> drawBuildingsCards(int num) {

    }

    private void initCards() {

    }

    private void initBuildingCards() {

    }
}
