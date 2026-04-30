package it.polimi.ingsw.controller.client.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.Pickable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundActionState extends ViewState {
    List<Pickable> topRow;
    List<Pickable> bottomRow;
    List<Pickable> topPicks = new ArrayList<>();
    int topRowPickable = 0;
    int bottomRowPickable = 0;
    List<Pickable> bottomPicks = new ArrayList<>();
    public RoundActionState(ClientController controller) {
        super(controller);
    }

    @Override
    public void render() {
        Board board = getController().getCurrLobby().getBoard();
        topRow = new ArrayList<>(board.getTopRow().getCharacterCards());
        topRow.addAll(board.getTopRow().getBuildingCards());

        bottomRow =  new ArrayList<>(board.getBottomRow().getCharacterCards());
        bottomRow.addAll(board.getBottomRow().getBuildingCards());

        System.out.println("Current Player: " + board.getGame().getGameState().getCurrPlayer().getName());
        System.out.println("Order TIle: " + String.join(", ",
                Arrays.stream(board.getOrderTile()).map(o -> o.getAssignedPlayer().getName()).toList()));
        System.out.println();

        System.out.println("TOP ROW PICKABLE CARDS:\n");
        for(int i = 0; i < topRow.size(); i++){
            System.out.println("    " + (i+1) + ". " + topRow.get(i) + "\n");
        }

        System.out.println("BOTTOM ROW PICKABLE CARDS:\n");
        for(int j = topRow.size();  j < topRow.size() + bottomRow.size(); j++){
            System.out.println("    " + (j+1) + ". " + bottomRow.get(j-topRow.size()) + "\n");
        }

        if(isTurn()){
            OfferTile offerTile = Arrays.stream(board.getOfferTrack())
                    .filter(o -> o.getAssignedPlayer().getName().equals(getController().getPlayerName()))
                    .findFirst().orElse(null);
            System.out.println("Pick a card by entering the number.");
            topRowPickable = offerTile.getTopRowPickable();
            System.out.println("    Remaining picks from top row: " + topRowPickable);
            bottomRowPickable = offerTile.getBottomRowPickable();
            System.out.println("    Remaining picks from bottom row: " +  bottomRowPickable);
            System.out.println("[0] End your turn.");
        } else {
            System.out.println("Waiting for " + board.getGame().getGameState().getCurrPlayer().getName());
        }

    }


    /** The player can pick multiple cards by continuously interacting with the TUI.
     * Once all the cards have been picked the player confirms with "0".
     * The list for the pickable cards are updated in the render method to keep track of the available cards,
     * but because players take turn it shouldn't be updated during a player's turn, meaning that the inserted index matches the desired one without conflict.
     * */
    @Override
    public void handleInput(String input) {
        if(isTurn()){
            if (input.equals("0")) {
                getController().getServer().requestPick(getController().getID(), getController().getCurrLobby().getLobbyID(),
                        topPicks, bottomPicks);
            } else {
                try {
                    int i = Integer.parseInt(input);
                    if (i < 0 || i > topRow.size() + bottomRow.size()) {
                        System.out.println("Please enter a valid number.");
                    } else if (i <= topRow.size()) {
                        if(topPicks.size() < topRowPickable)
                            topPicks.add(topRow.get(i-1));
                        else
                            System.out.println("No picks left from top row.");
                    } else {
                        if(bottomPicks.size() < bottomRowPickable)
                            bottomPicks.add(bottomRow.get(i-1-topRow.size()));
                        else
                            System.out.println("No picks left from bottom row.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        } else {
            System.out.println("Please wait for your turn.");
        }
    }

    private boolean isTurn() {
        return super.getController().getCurrLobby().getBoard().getGame().getGameState().getCurrPlayer().getName().equals(super.getController().getPlayerName());
    }
}
