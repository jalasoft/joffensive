package cz.jalasoft.joffensive.core;


import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;
import cz.jalasoft.joffensive.core.weapon.WeaponRegistry;
import cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponAnnotationIntrospection;

import java.util.Collection;
import java.util.NoSuchElementException;

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

    private final WeaponRegistry weaponRegistry;

    private JOffensive() {
        weaponRegistry = new WeaponRegistry();
    }

    //------------------------------------------------------------------
    //WEAPON REGISTRATION
    //------------------------------------------------------------------

    public void registerWeapon(String name, Weapon weapon) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of weapon must not be null or empty.");
        }

        if (weaponRegistry.weapon(name).isPresent()) {
            throw new IllegalArgumentException("Weapon with name '" + name + "' already exists.");
        }

        weaponRegistry.registerWeapon(name, weapon);
    }

    public void registerWeaponByType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Class must not be null.");
        }

        WeaponDefinition definition = WeaponAnnotationIntrospection.introspectType(type);

        weaponRegistry.registerWeapon(definition);
    }

    public void registerWeaponsInPackage(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            throw new IllegalArgumentException("Package name must not be null or empty.");
        }

        Collection<WeaponDefinition> definitions  = WeaponAnnotationIntrospection.introspectPackage(packageName);
        weaponRegistry.registerWeapons(definitions);
    }

    //----------------------------------------------------------------------
    //CAMP
    //----------------------------------------------------------------------

    public TrainingCamp trainingCamp() {
        return new TrainingCamp();
    }

    //-----------------------------------------------------------------------
    //WEAPON
    //-----------------------------------------------------------------------

    public Weapon weapon(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of weapon must not be null or empty.");
        }

        return weaponRegistry.weapon(name).orElseThrow(() -> new NoSuchElementException("No weapon of name '" + name + "' exists."));
    }
}
