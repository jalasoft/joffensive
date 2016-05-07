package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public interface Weapon {

    default void prepareWeapon() throws Exception {}

    Recoil shoot() throws Exception;

    default void cleanupWeapon() throws Exception {}
}
