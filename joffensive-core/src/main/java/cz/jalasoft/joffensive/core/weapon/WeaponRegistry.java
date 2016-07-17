package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.WeaponsException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-30.
 */
public final class WeaponRegistry {

    private final Collection<Weapon> weapons;

    public WeaponRegistry(Collection<Weapon> weapons) {
        this.weapons = new ArrayList<>(weapons);
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

    public Collection<String> weaponNames() {
        return weapons.stream()
                .map(Weapon::name)
                .collect(toSet());
    }

    //--------------------------------------------------------------------
    //BULK LIFECYCLE ACTIONS
    //--------------------------------------------------------------------

    public void beforeWeapons() throws WeaponsException {
        performLifeCycleOperation(Weapon::beforeWeapon);
    }


    public void afterWeapons() throws WeaponsException {
        performLifeCycleOperation(Weapon::afterWeapon);
    }

    private void performLifeCycleOperation(LifeCycleAction action) throws WeaponsException {
        WeaponsException parentException = new WeaponsException();

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

    //-------------------------------------------------------------------------
    //LIFECYCLE ACTION
    //-------------------------------------------------------------------------

    private interface LifeCycleAction {
        void execute(Weapon w) throws Exception;
    }
}
