package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.utils.*;

import java.util.*;

public class Deck {
    final private int gameSize;
    private final Board board;

    private Queue<AbstractCard> charEventCards;
    private Queue<AbstractCard> buildingCards;

    private List<Integer> ageBuildingsCount;
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

    /**
     * Draws a certain number of character/event cards.
     * @param num Number of drawn cards
     * @return List of character/event cards.
     * */
    public List<AbstractCard> drawCards(int num) {
        List<AbstractCard> cards = new ArrayList<>();

        for(int i = 0; i < num; i++) {
            try {
                cards.add(charEventCards.pop());
            } catch (EmptyStackException e) {
                System.out.println("Attempting to draw from empty deck");
            }
        }

        return cards;
    }


    /**
     * Draws a certain number of building cards based on the current age.
     * @return List of building cards.
     * */
    public List<AbstractBuilding> drawBuildingsCards() {
        List<AbstractBuilding> buildings = new ArrayList<>();

        for (int i = 0; i < ageBuildingsCount.get(currentAge); i++) {
            AbstractBuilding building = buildingCards.poll();

            if (building != null)
                buildings.add(building);
            else
                System.out.println("Attempting to draw from empty deck");
        }

        return buildings;
    }


    /**
     * Updates the current age.
     * */
    public void changeAge() {
        this.currentAge += 1;
    }


    private void initCards() {

    }

    private void initBuildingCards() {

    }
}
