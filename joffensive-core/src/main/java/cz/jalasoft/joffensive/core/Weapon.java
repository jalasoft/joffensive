package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public interface Weapon {

    default void beforeWeapon() throws Exception {}

    default void beforeShoot() throws Exception {}

    Recoil shoot() throws Exception;

    default void afterShoot() throws Exception {}

    default void afterWeapon() throws Exception {}
}
