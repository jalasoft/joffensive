package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ConventionalBattle implements Battle {

    private final Collection<Future<?>> fightingWarriors;
    private final Headquarters headquarters;
    private final ExecutorService executor;

    ConventionalBattle(Collection<Future<?>> fightingWarriors, Headquarters headquarters, ExecutorService executor) {
        this.fightingWarriors = fightingWarriors;
        this.headquarters = headquarters;
        this.executor = executor;
    }

    @Override
    public void fire() {
        headquarters.fire();
    }

    @Override
    public void ceaseFire() {
        fightingWarriors.stream().forEach(f -> f.cancel(true));
    }
}
