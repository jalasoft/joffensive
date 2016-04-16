package cz.jalasoft.joffensice;

import cz.jalasoft.joffensice.platoon.RegularShootingCadenceTraining;
import cz.jalasoft.joffensice.platoon.TrainingCamp;
import cz.jalasoft.joffensice.weapon.Weapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive {

    public static JOffensive load() {
        return JOffensiveLoader.load();
    }

    public static RegularShootingCadenceTraining regularCadence() {

    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    JOffensive() {}

    public TrainingCamp trainingCamp() {

    }

    public <U extends Weapon> U weapon(Class<U> weaponType) {
        U weapon = (U) weaponType.newInstance();

        return weapon;
    }
}
