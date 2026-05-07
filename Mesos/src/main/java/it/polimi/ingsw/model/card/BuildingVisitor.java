package it.polimi.ingsw.model.card;


import it.polimi.ingsw.model.card.building.*;

public interface BuildingVisitor {
    void visit(OrderTileBuilding v);
    void visit(CavePaintingBuilding v);
    void visit(HuntBuilding v);
    void visit(ExtraActionBuilding v);
    void visit(BonusPPBuilding v);
    void visit(CharacterBonusBuilding v);
    void visit(BuilderDoublePPBuilding v);
    void visit(FullSetBuilding v);
    void visit(NewFullSetBuilding v);
    void visit(InventorPairBuilding v);
    void visit(SustenanceDiscountBuilding v);
}
