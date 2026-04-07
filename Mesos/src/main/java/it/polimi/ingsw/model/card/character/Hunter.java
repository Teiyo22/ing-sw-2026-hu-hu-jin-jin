package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.Player;

public class Hunter extends AbstractCharacter {
    private boolean hasIcon;

    public Hunter(int era, boolean hasIcon) {
        super(era);
        this.hasIcon = hasIcon;
    }

    @Override
    public String getCharacterTyper(){
        return "Hunter";
    }

    /**The hunter gives immediately food if he has the icon
     * so when we pick the card we check if it has the icon */
    @Override
    public void onPick(Player player) {
        player.getTribe().addHunter();
        if(hasIcon){
            player.addFood(player.getTribe().getHunterCount())
        }
    }

    @Override
    public void accept(CardVisitor v){
        v.doForHunter(this);
    }
}
