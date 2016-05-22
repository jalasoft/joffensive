package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.warrior.Warrior;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ConventionalBattle implements Battle {

    private final Collection<Warrior> warriors;
    private final Headquarters headquarters;
    private final ExecutorService executor;

    private Collection<Future<Void>> fightingWarriors;

    ConventionalBattle(Collection<Warrior> warriors, Headquarters headquarters, ExecutorService executor) {
        this.warriors = warriors;
        this.headquarters = headquarters;
        this.executor = executor;
    }

    @Override
    public void fire() {
        //TODO check status
        try {
            fightingWarriors = executor.invokeAll(warriors, 0l, TimeUnit.MILLISECONDS);
            headquarters.fire();
        } catch (InterruptedException exc) {
            throw new RuntimeException("Ja nevim co s tim....");
        }
    }

    @Override
    public void ceaseFire() {
        //TODO check status
        fightingWarriors.stream().forEach(f -> f.cancel(true));
        fightingWarriors = null;
    }

    @Override
    public void addObserver(BattleObserver observer, Duration notificationInterval) {

    }

    @Override
    public void removeObserver(BattleObserver observer) {

    }
}
