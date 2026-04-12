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
        resolveEvents();
        assignBonusPP();
        setLeaderboard();
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
        events = bottom.getEventCards();

        for(Event e: events){
            e.onEvent(game);
        }

        List<Sustenance> sustenance = bottom.getSustenanceEventCards();
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
        Player previous;
        Player current;

        playersList.sort(null);

        current = playersList.get(0);
        current.setRank(rank);
        for(int evaluatedPlayers=1; i<playersList.size(); evaluatedPlayers++){
            current = playersList.get(i);
            previous = playersList.get(i-1);

            if(current.compareTo(previous)==0){
                current.setRank(previous.getRank());
            }else{
                current.setRank(evaluatedPlayers+1);
            {
        }

    }
}
