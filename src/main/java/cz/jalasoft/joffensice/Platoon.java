package cz.jalasoft.joffensice;

import cz.jalasoft.joffensice.weapon.Weapon;
import cz.jalasoft.joffensice.http.HttpBattle;
import cz.jalasoft.joffensice.http.HttpWeapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class Platoon {

    public Platoon reGroup(Platoon another) {

    }

    public Battle fire(Weapon weapon) {
        if (weapon instanceof HttpWeapon) {
            return new HttpBattle();
        }

        throw new IllegalArgumentException("")
    }
}
