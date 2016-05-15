package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.platoon.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
final class Warrior implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Warrior.class);

    private final WarriorName name;
    private final Weapon weapon;
    private final Skill skill;
    private final Headquarters headquarters;

    public Warrior(WarriorName name, Weapon weapon, Skill skill, Headquarters headquarters) {
        this.name = name;
        this.weapon = weapon;
        this.skill = skill;
        this.headquarters = headquarters;
    }

    @Override
    public void run() {
       try {
           headquarters.warriorReadyForFight();

           runInterruptibly();
       } catch (InterruptedException exc) {
           Thread.currentThread().interrupt();
       }
    }

    private void runInterruptibly() throws InterruptedException {
        int secondsSinceStart = 0;

        while(true) {
            shootMagazineOut(secondsSinceStart++);
            SECONDS.sleep(1);
        }
    }

    private void shootMagazineOut(int secondsSinceStart) throws InterruptedException {
        if (skill.cadence().shootNow(secondsSinceStart)) {
            shootMagazineOut();
        } else {
            LOGGER.debug(name + ": .");
        }
    }

    private void shootMagazineOut() throws InterruptedException {
        for(int i=0; i<skill.magazineSize(); i++) {
            shootBulletInterruptibly();
        }
    }

    private void shootBulletInterruptibly() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            Thread.interrupted();
            throw new InterruptedException();
        }

        LOGGER.debug(name + ": --> --> -->");

        try {
            Recoil recoil = weapon.shoot();

        } catch (InterruptedException exc) {
            throw exc;
        } catch (Exception exc) {
            String message = String.format("Warrior %s shoot damaged bullet.", exc.getMessage());
            LOGGER.error(message, exc);
        }
    }
}
