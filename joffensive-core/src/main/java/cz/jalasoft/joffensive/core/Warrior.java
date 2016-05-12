package cz.jalasoft.joffensive.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public final class Warrior implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Warrior.class);

    private final Weapon weapon;
    private final TrainingCamp trainingCamp;

    public Warrior(Weapon weapon, TrainingCamp trainingCamp) {
        this.weapon = weapon;
        this.trainingCamp = trainingCamp;
    }

    @Override
    public void run() {
       try {
           runInterruptibly();
       } catch (InterruptedException exc) {
           Thread.currentThread().interrupt();
       }
    }

    private void runInterruptibly() throws InterruptedException {
        int secondsSinceStart = 0;

        while(true) {
            if (trainingCamp.cadence.shootNow(secondsSinceStart)) {
                shootMagazineOut();
            } else {
                LOGGER.debug(name + ": .");
            }

            secondsSinceStart++;

            SECONDS.sleep(1);
        }
    }

    private void shootMagazineOut() throws InterruptedException {

        for(int i=0; i<magazineSize; i++) {
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
            weapon.shoot();
        } catch (Exception exc) {
            //TODO
        }
    }
}
