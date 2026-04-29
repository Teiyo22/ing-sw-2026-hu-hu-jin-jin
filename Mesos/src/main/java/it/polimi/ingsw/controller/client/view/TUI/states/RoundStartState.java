package it.polimi.ingsw.controller.client.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.OfferTile;

public class RoundStartState extends ViewState {
    public RoundStartState(ClientController controller) {
        super(controller);
    }

    @Override
    public void render() {
        OfferTile[] offerTrack = super.getController().getCurrLobby().getBoard().getOfferTrack();
        System.out.println("Offer tiles:");
        for (int i = 1; i <= offerTrack.length; i++) {
            String line = String.format("%d. Food Bonus: %d  |  Picks from top row: %d  |  Picks from bottom row: %d  |  Player: ",
                    i, offerTrack[i-1].getBonusFood(), offerTrack[i-1].getTopRowPickable(), offerTrack[i-1].getBottomRowPickable());
            if(offerTrack[i-1].getAssignedPlayer() != null){
                line = line + offerTrack[i-1].getAssignedPlayer().getName();
            }
            System.out.println(line);
        }
        if(isTurn()) {
            System.out.println();
            System.out.println("Select an available offer tile.");
        } else {
            System.out.println();
            System.out.println("Waiting for " + super.getController().getCurrLobby().getBoard().getGame().getGameState().getCurrPlayer().getName());
        }
    }


    //TODO: add pickOffer method in serverTCPInterface, add related message and method on server side.
    @Override
    public void handleInput(String input) {
        if(isTurn()) {
            try {
                int i = Integer.parseInt(input);
                OfferTile[] offerTrack = super.getController().getCurrLobby().getBoard().getOfferTrack();
                if (i <= 0 || i > offerTrack.length) {
                    System.out.println("Invalid number.");
                } else if (offerTrack[i - 1].getAssignedPlayer() == null) {
                    super.getController().getServer().pickOffer(super.getController().getID(), super.getController().getCurrLobby().getLobbyID(), i);
                } else {
                    System.out.println("This offer tile has already been taken.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid offer tile.");
            }
        } else {
            System.out.println("Please wait for your turn.");
        }
    }

    private boolean isTurn() {
        return super.getController().getCurrLobby().getBoard().getGame().getGameState().getCurrPlayer().getName().equals(super.getController().getPlayerName());
    }
}
