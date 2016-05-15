package cz.jalasoft.joffensive.core.platoon;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.BattleBootstrap;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-10.
 */
public abstract class AbstractPlatoon implements Platoon {

    private final BattleBootstrap battleBootstrap;

    public AbstractPlatoon(BattleBootstrap battleBootstrap) {
        this.battleBootstrap = battleBootstrap;
    }

    public abstract Collection<SinglePlatoon> platoons();

    @Override
    public final Platoon regroup(Platoon other) {
        if (!(other instanceof AbstractPlatoon)) {
            throw new IllegalArgumentException("Unexpected type of platoon: " + other.getClass().getName());
        }

        AbstractPlatoon theOther = (AbstractPlatoon) other;

        return new CompositePlatoon(this.platoons(), theOther.platoons(), battleBootstrap);
    }

    @Override
    public final Battle fire(Weapon weapon) {
        ExecutorService executor = Executors.newCachedThreadPool();
        return fire(weapon, executor);
    }

    @Override
    public final Battle fire(Weapon weapon, ExecutorService executor) {
        Battle battle = battleBootstrap.initiate(this, weapon, executor);
        battle.fire();

        return battle;
    }
}
