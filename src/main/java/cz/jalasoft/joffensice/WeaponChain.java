package cz.jalasoft.joffensice;

import cz.jalasoft.joffensice.weapon.Weapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public interface WeaponChain<T extends Weapon> {

    T and();
}
