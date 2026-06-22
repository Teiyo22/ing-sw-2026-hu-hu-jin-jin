package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.Inventor;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

import java.util.List;

public class BuildingHandler implements BuildingVisitor {
    private Player lastPlayer;
    private AbstractCard lastPickedCard;
    private OrderSlot lastOrderSlot;
    private ExtraActionState lastExtraActionState;

    private final List<VisitableBuilding> cardPickBuildings;
    private final List<VisitableBuilding> huntBuildings;
    private final List<VisitableBuilding> cavePaintingBuildings;
    private final List<VisitableBuilding> orderTileBuildings;
    private final List<VisitableBuilding> extraActionBuildings;
    private final List<VisitableBuilding> gameEndBuildings;
    private final List<VisitableBuilding> sustenanceBuildings;

    public BuildingHandler() {
        this.cardPickBuildings = new ArrayList<>();
        this.huntBuildings = new ArrayList<>();
        this.cavePaintingBuildings = new ArrayList<>();
        this.orderTileBuildings = new ArrayList<>();
        this.extraActionBuildings = new ArrayList<>();
        this.gameEndBuildings = new ArrayList<>();
        this.sustenanceBuildings = new ArrayList<>();
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects when a card is picked.
     *
     * @param building the building to add to the card pick buildings list
     */
    public void addSustenanceBuilding(VisitableBuilding building){
        sustenanceBuildings.add(building);
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects when a card is picked.
     *
     * @param building the building to add to the card pick buildings list
     */
    public void addCardPickBuilding(VisitableBuilding building){
        cardPickBuildings.add(building);
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects during the hunt event.
     *
     * @param building the building to add to the hunt buildings list
     */
    public void addHuntBuilding(VisitableBuilding building){
        huntBuildings.add(building);
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects during the cave painting event.
     *
     * @param building the building to add to the cave painting buildings list
     */
    public void addCavePaintingBuilding(VisitableBuilding building){
        cavePaintingBuildings.add(building);
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects when a player is assigned to an order tile.
     *
     * @param building the building to add to the order tile buildings list
     */
    public void addOrderTileBuilding(VisitableBuilding building){
        orderTileBuildings.add(building);
    }

    /**
     * Registers a {@link VisitableBuilding} that triggers effects at the end of the game.
     *
     * @param building the building to add to the game end buildings list
     */
    public void addGameEndBuilding(VisitableBuilding building){
        gameEndBuildings.add(building);
    }

    /**
     * Registers an {@link ExtraActionBuilding} that grants an extra action after all offers are resolved.
     *
     * @param building the building to add to the extra action buildings list
     */
    public void addExtraActionBuilding(ExtraActionBuilding building){
        extraActionBuildings.add(building);
    }

    /**
     * After a card has been picked, apply the effects of the card pick buildings.
     * The effects consist in receiving different bonuses depending on the building and picked card.
     * */
    public void applyCardPickEffects(AbstractCard pickedCard, Player player) {
        lastPickedCard = pickedCard;
        lastPlayer = player;

        for(VisitableBuilding building: cardPickBuildings){
            building.accept(this);
        }

        lastPickedCard = null;
        lastPlayer = null;
    }

    public void applySustenanceEffect() {
        sustenanceBuildings.stream()
                .map(b -> ((AbstractBuilding) b).getOwner().getTribe())
                .distinct()
                .forEach(tribe -> tribe.setSustenanceDiscount(tribe.getCollectorCount()*3));

        for(VisitableBuilding building: sustenanceBuildings){
            building.accept(this);
        }
    }


    /**
     * During the hunt event, apply the effects of the hunt buildings.
     * The effects consist in receiving a bonus food and bonus PP depending on the number of hunters.
     * */
    public void applyHuntEffects() {
        for(VisitableBuilding building: huntBuildings){
            building.accept(this);
        }
    }

    /**
     * During the cave painting event, apply the effects of the cave painting buildings.
     * The effects consist in receiving a bonus food depending on the number of artists.
     * */
    public void applyCavePaintingEffects() {
        for(VisitableBuilding building: cavePaintingBuildings){
            building.accept(this);
        }
    }

    /**
     * Apply the effects of the order tile buildings.
     * The effects consist in receiving a bonus PP depending on the order tile.
     * */
    public void applyOrderTileEffects(OrderSlot slot) {
        lastOrderSlot = slot;

        for(VisitableBuilding building: orderTileBuildings)
            building.accept(this);

        lastOrderSlot = null;
    }

    /**
     * After all offers are resolved, apply the effects of the extra action buildings.
     * Multiple extra action buildings can be present.
     * Extra action state passes to round end state if all extra actions are resolved.
     * */
    public void applyExtraActionEffects(ExtraActionState state, int buildingIndex) {
        if(buildingIndex < extraActionBuildings.size()){
            lastExtraActionState = state;
            extraActionBuildings.get(buildingIndex).accept(this);
        } else {
            state.setCurrPlayer(null);
        }

        lastExtraActionState = null;
    }

    /**
     * At the end of the game, apply the effects of all game end buildings.
     * Game end buildings effects consist in receiving bonus PP.
     * */
    public void applyGameEndEffects() {
        for(VisitableBuilding building: gameEndBuildings){
            building.accept(this);
        }
    }

    /**
     * Visits an {@link OrderTileBuilding} and grants its owner one food if they occupied
     * an order slot with a positive food delta.
     *
     * @param b the {@link OrderTileBuilding} being visited
     */
    @Override
    public void visit(OrderTileBuilding b) {
        if(lastOrderSlot == null)
            return;

        if(lastOrderSlot.getAssignedPlayer() == b.getOwner() && lastOrderSlot.getFoodDelta() > 0){
            b.getOwner().addFood(1);
        }
    }

    /**
     * Visits a {@link CavePaintingBuilding} and grants its owner bonus food
     * proportional to the number of artists in their tribe.
     *
     * @param b the {@link CavePaintingBuilding} being visited
     */
    @Override
    public void visit(CavePaintingBuilding b) {
        Player owner = b.getOwner();
        owner.addFood(owner.getTribe().getArtistCount() * b.getBonusFood());
    }

    /**
     * Visits a {@link HuntBuilding} and grants its owner bonus food and bonus PP
     * proportional to the number of hunters in their tribe.
     *
     * @param b the {@link HuntBuilding} being visited
     */
    @Override
    public void visit(HuntBuilding b) {
        Player owner = b.getOwner();
        owner.addFood(owner.getTribe().getHunterCount() * b.getBonusFood());
        owner.addPP(owner.getTribe().getHunterCount() * b.getBonusPP());
    }

    /**
     * Visits an {@link ExtraActionBuilding} and sets the current player in the
     * {@link ExtraActionState} to the building's owner, enabling an extra action for them.
     *
     * @param b the {@link ExtraActionBuilding} being visited
     */
    @Override
    public void visit(ExtraActionBuilding b) {
        if(lastExtraActionState != null)
            lastExtraActionState.setCurrPlayer(b.getOwner());
    }

    /**
     * Visits a {@link BonusPPBuilding} and grants its owner the building's fixed PP bonus.
     *
     * @param b the {@link BonusPPBuilding} being visited
     */
    @Override
    public void visit(BonusPPBuilding b) {
        b.getOwner().addPP(b.getBonusPP());
    }

    /**
     * Visits a {@link CharacterBonusBuilding} and grants its owner PP based on the count
     * of each character type in their tribe, weighted by the building's per-character PP bonuses.
     *
     * @param b the {@link CharacterBonusBuilding} being visited
     */
    @Override
    public void visit(CharacterBonusBuilding b) {
        Player owner = b.getOwner();

        owner.addPP(owner.getTribe().getInventorCount() * b.getInventorBonusPP() +
                        owner.getTribe().getShamanCount() * b.getShamanBonusPP() +
                        owner.getTribe().getHunterCount() * b.getHunterBonusPP() +
                        owner.getTribe().getCollectorCount() * b.getCollectorBonusPP() +
                        owner.getTribe().getArtistCount() * b.getArtistBonusPP() +
                        owner.getTribe().getBuilderCount() * b.getBuilderBonusPP());
    }

    /**
     * Visits a {@link BuilderDoublePPBuilding} and grants its owner PP based on the builder bonus PP.
     *
     * @param b the {@link BuilderDoublePPBuilding} being visited
     */
    @Override
    public void visit(BuilderDoublePPBuilding b) {
        b.getOwner().addPP(b.getOwner().getTribe().getBuilderBonusPP());
    }

    /**
     * Visits a {@link FullSetBuilding} and grants its owner 6 PP for each complete set
     * of characters held by their tribe (one of each type).
     *
     * @param b the {@link FullSetBuilding} being visited
     */
    @Override
    public void visit(FullSetBuilding b) {
        int setNum = b.getOwner().getTribe().getMinChar();
        b.getOwner().addPP(6 * setNum);
    }

    /**
     * Visits a {@link NewFullSetBuilding} and grants its owner 5 food each time they reach
     * a new personal minimum character count across all character types in their tribe.
     * The threshold is updated after each reward to avoid duplicate grants.
     *
     * @param b the {@link NewFullSetBuilding} being visited
     */
    @Override
    public void visit(NewFullSetBuilding b) {
        Player owner = b.getOwner();
        int minForSet = b.getMinForSet();

        if(owner.getTribe().getMinChar() >= minForSet){
            b.setMinForSet(owner.getTribe().getMinChar() + 1);
            owner.addFood(5);
        }
    }

    /**
     * Visits an {@link InventorPairBuilding} and grants its owner 3 food if the last picked
     * card was an {@link Inventor} and the owner completed a pair for that specific inventor type.
     *
     * @param b the {@link InventorPairBuilding} being visited
     */
    @Override
    public void visit(InventorPairBuilding b) {
        if (b.getOwner() == lastPlayer && lastPickedCard instanceof Inventor i) {
            if (b.getOwner().getTribe().getNumInventorType(i.getInventorType()) % 2 == 0) {
                b.getOwner().addFood(3);
            }
        }
    }

    /**
     * Visits a {@link SustenanceDiscountBuilding} and if it was picked by the building's owner,
     * ii increases the sustenance discount of the owner depending on the picked character's type.
     *
     * @param b the {@link SustenanceDiscountBuilding} being visited
     */
    @Override
    public void visit(SustenanceDiscountBuilding b) {
        Player owner = b.getOwner();

        owner.getTribe().addSustenanceDiscount(owner.getTribe().getInventorCount() * b.getInventorDiscount() +
                owner.getTribe().getShamanCount() * b.getShamanDiscount() +
                owner.getTribe().getHunterCount() * b.getHunterDiscount() +
                owner.getTribe().getCollectorCount() * b.getCollectorDiscount() +
                owner.getTribe().getArtistCount() * b.getArtistDiscount() +
                owner.getTribe().getBuilderCount() * b.getBuilderDiscount());
    }
}
