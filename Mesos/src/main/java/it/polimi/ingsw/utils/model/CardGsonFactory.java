package it.polimi.ingsw.utils.model;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.*;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.card.event.*;

import java.util.List;

public class CardGsonFactory {
    private static final List<Class<? extends AbstractCard>> subtypes = List.of(
            BonusPPBuilding.class,
            BuilderDoublePPBuilding.class,
            CavePaintingBuilding.class,
            CharacterBonusBuilding.class,
            ExtraActionBuilding.class,
            FullSetBuilding.class,
            HuntBuilding.class,
            InventorPairBuilding.class,
            NewFullSetBuilding.class,
            OrderTileBuilding.class,
            RitualDoubleBonusBuilding.class,
            RitualNoLossBuilding.class,
            RitualStarsBuilding.class,
            SustenanceDiscountBuilding.class,
            Artist.class,
            Builder.class,
            Collector.class,
            Hunter.class,
            Inventor.class,
            Shaman.class,
            CavePainting.class,
            Hunt.class,
            ShamanicRitual.class,
            Sustenance.class
            );

    public static <T> RuntimeTypeAdapterFactory<T> buildFactory(Class<T> base) {
        RuntimeTypeAdapterFactory<T> factory = RuntimeTypeAdapterFactory.of(base, "type");

        subtypes.stream()
                .filter(base::isAssignableFrom)
                .forEach(c -> factory.registerSubtype(
                        (Class<? extends T>) c,
                        c.getSimpleName()
                ));

        return factory;
    }
}
