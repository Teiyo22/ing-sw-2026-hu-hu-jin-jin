package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class GameEndState extends GameState {
    private final Row bottom;
    private final Row top;
    private List<Player> playersList;

    public GameEndState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.playersList = game.getPlayers();
        this.bottom = game.getBoard().getBottomRow();
        this.top = game.getBoard().getTopRow();
    }

    @Override
    public void update() {

    }

    private void resolveEvents() {
        List<Event> events = top.getEventCards();

        for(Event e: events){
            e.onEvent(game);
        }

        List<Sustenance> sustenance = top.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.onEvent(game);
        }


    }

    private void assignBonusPP(Player p) {
        int PPbonus=0;
        PPbonus = p.getBuilderBonusPP() + p.getInventorBonusPP() + 10*(p.ArtistCount()/2);
        p.addPP(PPbonus);
        buildingHandler.applyGameEndEffects();
    }

    private void setLeaderboard() {
        int rank=1;
        Player precedente;
        Player corrente = playersList.get(0);

        playersList.sort(null);
        corrent.setRank(rank);
        for(int i=1; i<playersList.size(); i++){
            corrente = playersList.get(i);
            precedente = playersList.get(i-1);

            if(corrente.compareTo(precedente)==0){
                corrente.setRank(precedente.getRank());
            }else{
                corrente.setRank(rank+1);
            {
            rank++;
        }
    }
}
