package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.platoon.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-16.
 */
final class ShootDrivingWarriorDecorator implements Warrior {

    public static final Logger LOGGER = LoggerFactory.getLogger(ShootDrivingWarriorDecorator.class);

    private final Warrior warrior;
    private final Skill skill;

    public ShootDrivingWarriorDecorator(Warrior warrior, Skill skill) {
        this.warrior = warrior;
        this.skill = skill;
    }

    @Override
    public WarriorName name() {
        return warrior.name();
    }

    @Override
    public void fight() {
        try {
            shootMagazineInteruptibly();
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
    }

    private void shootMagazineInteruptibly() throws InterruptedException {
        for(int i=0; i<skill.magazineSize(); i++) {
            interruptIfRequested();
            shootBulletInterruptibly();
        }
    }

    private void interruptIfRequested() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            LOGGER.debug("Interruption requested for warrion {}", warrior.name());

            //reset the status
            Thread.interrupted();
            throw new InterruptedException();
        }
    }

    private void shootBulletInterruptibly() {
        warrior.fight();
    }
}
