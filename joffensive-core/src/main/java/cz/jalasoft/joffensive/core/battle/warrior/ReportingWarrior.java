package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.Headquarters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
final class ReportingWarrior implements Warrior {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportingWarrior.class);

    private final WarriorName name;
    private final Weapon weapon;
    private final Headquarters headquarters;

    public ReportingWarrior(WarriorName name, Weapon weapon, Headquarters headquarters) {
        this.name = name;
        this.weapon = weapon;
        this.headquarters = headquarters;
    }

    @Override
    public WarriorName name() {
        return name;
    }

    @Override
    public void fight() {
        try {
            Recoil recoil = weapon.shoot();

        } catch (Exception exc) {

        }
    }
}
