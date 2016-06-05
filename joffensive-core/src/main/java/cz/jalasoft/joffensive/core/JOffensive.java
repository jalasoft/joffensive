package cz.jalasoft.joffensive.core;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import cz.jalasoft.joffensive.core.battle.ConventionalBattle;
import cz.jalasoft.joffensive.core.battle.Headquarters;
import cz.jalasoft.joffensive.core.battle.warrior.Warrior;
import cz.jalasoft.joffensive.core.battle.warrior.WarriorFactory;
import cz.jalasoft.joffensive.core.weapon.WeaponRegistry;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive implements AutoCloseable {

    public static JOffensiveConfigurer newOffensive() {
        Config config = ConfigFactory.load();
        return new JOffensiveConfigurer(config);
    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    private final WeaponRegistry weaponRegistry;
    private final WarriorFactory warriorFactory;

    JOffensive(Configuration configuration, Collection<Weapon> weapons) throws WeaponsException {
        this.weaponRegistry = new WeaponRegistry(weapons);
        this.warriorFactory = new WarriorFactory(configuration);
    }

    //----------------------------------------------------------------------
    //WEAPONS LIFECYCLE
    //----------------------------------------------------------------------

    final void beforeWeapons() throws WeaponsException {
        weaponRegistry.beforeWeapons();
    }

    private void afterWeapons() throws WeaponsException {
        weaponRegistry.afterWeapons();
    }

    //----------------------------------------------------------------------
    //CAMP
    //----------------------------------------------------------------------

    public TrainingCamp trainingCamp() {
        return new TrainingCamp(this::newBattle);
    }

    //----------------------------------------------------------------------
    //CALLBACKS
    //----------------------------------------------------------------------

    private Battle newBattle(Platoon platoon, Weapon weapon, ExecutorService customExecutor) {
        Headquarters headquarters = new Headquarters();
        Collection<Warrior> warriors = warriorFactory.produceWarriors(platoon, weapon, headquarters);

        ExecutorService executor = ofNullable(customExecutor).orElseGet(this::defaultBattleExecutor);

        return new ConventionalBattle(warriors, headquarters, executor);
    }

    private ExecutorService defaultBattleExecutor() {
        return newCachedThreadPool();
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

    public Collection<String> weapons() {
        return weaponRegistry.weaponNames();
    }

    //------------------------------------------------------------------------
    //CLOSING
    //------------------------------------------------------------------------

    @Override
    public void close() throws WeaponsException {
        afterWeapons();
    }
}
