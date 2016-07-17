package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.warrior.Warrior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ConventionalBattle implements Battle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConventionalBattle.class);

    private final Collection<Warrior> warriors;
    private final Weapon weapon;
    private final Headquarters headquarters;
    private final ExecutorService executor;

    private Collection<Future<Void>> fightingWarriors;

    public ConventionalBattle(Collection<Warrior> warriors, Weapon weapon, Headquarters headquarters, ExecutorService executor) {
        this.warriors = warriors;
        this.weapon = weapon;
        this.headquarters = headquarters;
        this.executor = executor;
    }

    @Override
    public void fire() {
        if (fightingWarriors != null) {
            throw new IllegalStateException("There are already some warriors fighting. First invoke ceaseFire()");
        }

        invokeBeforeShooting();

        try {
            fightingWarriors = warriors.stream()
                    .map(executor::submit)
                    .collect(toList());

            headquarters.startingLatch().countDown();

            executor.execute(new AfterShootingLifecycleMethodInvoker());
        } catch (Exception exc) {
            invokeAfterShooting();
        }
    }

    private void invokeBeforeShooting() {
        try {
            weapon.beforeShooting();
        } catch (Exception exc) {
            LOGGER.error("Before shooting of a addWeapon failed.", exc);
        }
    }

    private void invokeAfterShooting() {
        try {
            weapon.afterShooting();
        } catch (Exception exc) {
            LOGGER.error("After shooting of a addWeapon failed.", exc);
        }
    }

    @Override
    public void ceaseFire() {
        if (fightingWarriors == null) {
            throw new IllegalStateException("There are no warriors currently fighting.");
        }

        fightingWarriors.stream().forEach(f -> f.cancel(true));
        fightingWarriors = null;
    }

    //---------------------------------------------------------------------------------
    //TASK FOR CALLING LIFECYCLE AFTER-SHOOTING WEAPON METHOD
    //---------------------------------------------------------------------------------

    private final class AfterShootingLifecycleMethodInvoker implements Runnable {

        @Override
        public void run() {
            for(Future<Void> f : fightingWarriors) {
                try {
                    f.get();
                } catch(InterruptedException exc) {
                    //TODO
                    System.out.println("SELHALO TO");
                } catch (ExecutionException exc) {
                    System.out.println("Selhalo to");
                    //TODO?
                }
            }

            invokeAfterShooting();
        }
    }
}
