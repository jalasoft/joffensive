package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.battle.warrior.Warrior;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ConventionalBattle implements Battle {

    private final Collection<Warrior> warriors;
    private final Headquarters headquarters;
    private final ExecutorService executor;

    private Collection<Future<Void>> fightingWarriors;

    public ConventionalBattle(Collection<Warrior> warriors, Headquarters headquarters, ExecutorService executor) {
        this.warriors = warriors;
        this.headquarters = headquarters;
        this.executor = executor;
    }

    @Override
    public void fire() {
        if (fightingWarriors != null) {
            throw new IllegalStateException("There are already some warriors fighting. First invoke ceaseFire()");
        }

        fightingWarriors = warriors.stream()
                .map(executor::submit)
                .collect(toList());

        headquarters.startingLatch().countDown();
    }

    @Override
    public void ceaseFire() {
        if (fightingWarriors == null) {
            throw new IllegalStateException("There are no warriors currently fighting.");
        }

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
