package cz.jalasoft.joffensive.core.journal.message;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-09.
 */
public final class Messages {

    public static WeaponLifecycleMessage beforeWeapon(String name) {
        return new WeaponLifecycleMessage(name, WeaponLifecycleMessage.Type.BEFORE_WEAPON);
    }

    private Messages() {}
}
