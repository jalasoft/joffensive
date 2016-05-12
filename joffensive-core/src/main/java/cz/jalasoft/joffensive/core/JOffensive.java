package cz.jalasoft.joffensive.core;


import cz.jalasoft.joffensive.core.battle.BattleBootstrap;
import cz.jalasoft.joffensive.core.weapon.WeaponFactory;
import cz.jalasoft.joffensive.core.weapon.WeaponRegistry;
import cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponIntrospectionException;

import java.util.NoSuchElementException;

import static cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponAnnotationIntrospection.forPackage;
import static cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponAnnotationIntrospection.forType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive {

    public static JOffensive newOffensive() {
        return new JOffensive();
    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    private final WeaponFactory weaponFactory;
    private final WeaponRegistry weaponRegistry;

    private final BattleBootstrap battleBootstrap;

    private JOffensive() {
        this.weaponFactory = new WeaponFactory();
        this.weaponRegistry = new WeaponRegistry();

        this.battleBootstrap = new BattleBootstrap();
    }

    //------------------------------------------------------------------
    //WEAPON REGISTRATION
    //------------------------------------------------------------------

    public void registerWeapon(String name, Weapon weapon) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of weapon must not be null or empty.");
        }

        if (weaponRegistry.hasWeapon(name)) {
            throw new IllegalArgumentException("Weapon with name '" + name + "' already exists.");
        }

        weaponRegistry.registerWeapon(name, weapon);
    }

    public void registerWeaponByType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Class must not be null.");
        }

        try {
            forType(type)
                    .introspect()
                    .stream()
                    .map(weaponFactory::newWeapon)
                    .forEach(w -> weaponRegistry.registerWeapon(w.name(), w));
        } catch (WeaponIntrospectionException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void registerWeaponsInPackage(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            throw new IllegalArgumentException("Package name must not be null or empty.");
        }
        try {
            forPackage(packageName)
                    .introspect()
                    .stream()
                    .map(weaponFactory::newWeapon)
                    .forEach(w -> weaponRegistry.registerWeapon(w.name(), w));
        } catch (WeaponIntrospectionException exc) {
            throw new RuntimeException(exc);
        }
    }

    //----------------------------------------------------------------------
    //CAMP
    //----------------------------------------------------------------------

    public TrainingCamp trainingCamp() {
        return new TrainingCamp(battleBootstrap);
    }

    //-----------------------------------------------------------------------
    //WEAPON
    //-----------------------------------------------------------------------

    public Weapon weapon(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of weapon must not be null or empty.");
        }

        if (!weaponRegistry.hasWeapon(name)) {
            new NoSuchElementException("No weapon of name '" + name + "' exists.");
        }

        return weaponRegistry.weapon(name);
    }
}
