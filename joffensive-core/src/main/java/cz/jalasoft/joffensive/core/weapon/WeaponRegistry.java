package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Weapon;

import java.util.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-30.
 */
public final class WeaponRegistry {

    private final Map<String, Weapon> weapons;

    public WeaponRegistry() {
        this.weapons = new HashMap<>();
    }

    public void registerWeapon(Weapon weapon) {
        if (weapons.containsKey(weapon.name())) {
            throw new IllegalArgumentException("Weapon called '" + weapon.name() + "' is already registered.");
        }

        weapons.put(name, weapon);
    }

    public boolean hasWeapon(String name) {
        return weapons.containsKey(name);
    }

    public Weapon weapon(String name) {
        if (!hasWeapon(name)) {
            throw new IllegalArgumentException("Weapon called '" + name + "' does not exists.");
        }
        return weapons.get(name);
    }


}
