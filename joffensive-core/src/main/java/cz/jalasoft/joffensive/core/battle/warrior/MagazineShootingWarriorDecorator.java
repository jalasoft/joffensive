package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.platoon.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-16.
 */
class MagazineShootingWarriorDecorator implements Warrior {

    public static final Logger LOGGER = LoggerFactory.getLogger(MagazineShootingWarriorDecorator.class);

    private final Warrior warrior;
    private final Skill skill;

    MagazineShootingWarriorDecorator(Warrior warrior, Skill skill) {
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

    void beforeMagazineShootOut() {}

    private void shootMagazineInteruptibly() throws InterruptedException {
            beforeMagazineShootOut();

            try {
                for (int i = 0; i < skill.magazineSize(); i++) {
                    interruptIfRequested();
                    shootBulletInterruptibly();
                }
            } finally {
                afterMagazineShootOut();
            }
    }

    void afterMagazineShootOut() {}

    private void interruptIfRequested() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            LOGGER.debug("Interruption requested for warrior {}", warrior.name());

            //reset the status
            Thread.interrupted();
            throw new InterruptedException();
        }
    }

    private void shootBulletInterruptibly() {
        warrior.fight();
    }
}
