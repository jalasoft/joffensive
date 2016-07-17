package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.platoon.Skill;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-22.
 */
final class CadenceDrivingWarriorDecorator implements Warrior {

    private final Warrior warrior;
    private final Skill skill;

    CadenceDrivingWarriorDecorator(Warrior warrior, Skill skill) {
        this.warrior = warrior;
        this.skill = skill;
    }

    @Override
    public WarriorName name() {
        return warrior.name();
    }

    @Override
    public void fight() {
        int secondsSinceStart = 0;

        while(true) {
            shootMagazinesOut(secondsSinceStart++);

            if (waitOneSecondInterruptibly()) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private boolean waitOneSecondInterruptibly() {
        try {
            SECONDS.sleep(1);
            return false;
        } catch (InterruptedException exc) {
            return true;
        }
    }

    private void shootMagazinesOut(int secondsSinceStart) {
        if (skill.cadence().canShootNow(secondsSinceStart)) {
            warrior.fight();
        }
    }
}
