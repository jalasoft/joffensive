package cz.jalasoft.joffensive.core.journal.message;

import cz.jalasoft.joffensive.core.journal.message.DefaultMessage;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-09.
 */
public final class WeaponLifecycleMessage extends DefaultMessage {

    public enum Type {
        BEFORE_WEAPON, BEFORE_SHOOTING, AFTER_SHOOTING, AFTER_WEAPON
    }

    private final String weaponName;
    private final Type type;

    WeaponLifecycleMessage(String weaponName, Type type) {
        super("WeaponLifecycle");

        this.weaponName = weaponName;
        this.type = type;
    }

    public String weaponName() {
        return weaponName;
    }

    public Type type() {
        return type;
    }

    //TODO
}
