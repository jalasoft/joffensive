package cz.jalasoft.joffensive.core.battle.warrior;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-06.
 */
public interface WarriorExceptionHandler {

    default void handleBeforeWeaponException(Exception exc) {}

    default void handleBeforeShootException(Exception exc) {}

    default void handleShootException(Exception exc) {}

    default void handleAfterShootException(Exception exc) {}

    default void handleAfterWeaponException(Exception exc) {}
}
