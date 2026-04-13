package it.polimi.ingsw.model.card.event;

public class ShamanicRitual extends AbstractEvent {
    private final int bonusPP;
    private final int malusPP;

    public ShamanicRitual(String type, int era, boolean isFinal, int bonusPP, int malusPP) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    @Override
    public void onEvent(Game game) {

    }
}
