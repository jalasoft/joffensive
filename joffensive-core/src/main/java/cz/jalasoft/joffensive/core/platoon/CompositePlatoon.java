package cz.jalasoft.joffensive.core.platoon;

import cz.jalasoft.joffensive.core.battle.BattleBootstrap;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-25.
 */
public final class CompositePlatoon extends AbstractPlatoon {

    private final Collection<SinglePlatoon> platoons;

    CompositePlatoon(Collection<SinglePlatoon> singles, Collection<SinglePlatoon> singles2, BattleBootstrap battleBootstrap) {
        super(battleBootstrap);

        platoons = new ArrayList<>();
        platoons.addAll(singles);
        platoons.addAll(singles2);
    }

    @Override
    public Collection<SinglePlatoon> platoons() {
        return platoons;
    }

}
