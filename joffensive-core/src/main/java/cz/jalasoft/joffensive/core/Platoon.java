package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public interface Platoon {

    void fire(Weapon weapon);

    Platoon regroup(Platoon recruits);
}
