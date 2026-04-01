package it.polimi.ingsw.model.card.event;

public class ShamanicRitual extends AbstractEvent {
    private final int bonusPP;
    private final int malusPP;

    public ShamanicRitual(int era, int bonusPP, int malusPP) {
        super(era);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    @Override
    public void onEvent(Game game) {

    }
}
