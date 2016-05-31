package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Weapon;

import java.util.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-30.
 */
public final class WeaponRegistry {

    private final Collection<Weapon> weapons;

    public WeaponRegistry(Collection<Weapon> weapons) {
        this.weapons = new ArrayList<>(weapons);
    }

    public void registerWeapon(Weapon weapon) {
        if (hasWeapon(weapon.name())) {
            throw new IllegalArgumentException("Weapon called '" + weapon.name() + "' is already registered.");
        }

        weapons.add(weapon);
    }

    public boolean hasWeapon(String name) {
        return weapons.stream().map(Weapon::name).anyMatch(n -> n.equals(name));
    }

    public Weapon weapon(String name) {
        if (!hasWeapon(name)) {
            throw new IllegalArgumentException("Weapon called '" + name + "' does not exists.");
        }

        return weapons.stream()
                .filter(w -> w.name().equals(name))
                .findAny()
                .get();
    }

    public void beforeWeapons() throws Exception {
        performLifeCycleOperation(Weapon::beforeWeapon);
    }


    public void afterWeapons() throws Exception {
        performLifeCycleOperation(Weapon::afterWeapon);
    }

    private void performLifeCycleOperation(LifeCycleAction action) throws Exception {
        Exception parentException = new Exception();

        weapons.forEach(w -> {
            try {
                action.execute(w);
            } catch (Exception exc) {
                parentException.addSuppressed(exc);
            }
        });

        if (parentException.getSuppressed().length > 0) {
            throw parentException;
        }
    }

    private interface LifeCycleAction {
        void execute(Weapon w) throws Exception;
    }
}
