package cz.jalasoft.joffensive.core;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import cz.jalasoft.joffensive.core.battle.BattleBootstrap;
import cz.jalasoft.joffensive.core.weapon.WeaponFactory;
import cz.jalasoft.joffensive.core.weapon.WeaponRegistry;
import cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponIntrospectionException;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponAnnotationIntrospection.forPackage;
import static cz.jalasoft.joffensive.core.weapon.annotation.introspection.WeaponAnnotationIntrospection.forType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive {

    public static JOffensive defaultOffensive() {
        return custom().get();
    }

    public static JOffensiveConfigurer custom() {
        Config config = ConfigFactory.load();
        return new JOffensiveConfigurer(config);
    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    private final WeaponRegistry weaponRegistry;

    private final WeaponFactory weaponFactory;
    private final BattleBootstrap battleBootstrap;

    JOffensive(Configuration configuration) {
        this.weaponRegistry = new WeaponRegistry();

        this.weaponFactory = new WeaponFactory();
        this.battleBootstrap = new BattleBootstrap(configuration);
    }

    //------------------------------------------------------------------
    //WEAPON REGISTRATION
    //------------------------------------------------------------------

    public void registerWeapon(String name, Weapon weapon) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of weapon must not be null or empty.");
        }

        if (weaponRegistry.hasWeapon(name)) {
            throw new IllegalArgumentException("Weapon with called '" + name + "' already exists.");
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
            throw new IllegalArgumentException("Package called must not be null or empty.");
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
            new NoSuchElementException("No weapon of called '" + name + "' exists.");
        }

        return weaponRegistry.weapon(name);
    }
}
