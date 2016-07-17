package cz.jalasoft.joffensive.core;


import cz.jalasoft.joffensive.core.battle.ConventionalBattle;
import cz.jalasoft.joffensive.core.battle.Headquarters;
import cz.jalasoft.joffensive.core.battle.warrior.Warrior;
import cz.jalasoft.joffensive.core.battle.warrior.WarriorFactory;
import cz.jalasoft.joffensive.core.weapon.WeaponRegistry;
import cz.jalasoft.joffensive.core.weapon.definition.WeaponDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JOffensive.class);

    public static JOffensiveConfigurer offensive() {
        return new JOffensiveConfigurer();
    }

    //-----------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------

    private final WeaponRegistry weaponRegistry;
    private final WarriorFactory warriorFactory;

    JOffensive(Configuration configuration) throws WeaponsException {
        this.weaponRegistry = new WeaponRegistry(loadWeapons(configuration));
        this.weaponRegistry.beforeWeapons();

        this.warriorFactory = new WarriorFactory(configuration);
    }

    private Collection<Weapon> loadWeapons(Configuration configruation) {
        LOGGER.info("Loading weapons...");

        Collection<Weapon> weapons = configruation.weaponDefinitions()
                .stream()
                .map(WeaponDefinition::create)
                .flatMap(Collection::stream)
                .peek(weapon -> LOGGER.debug("Weapon '{}' loaded", weapon.name()))
                .collect(Collectors.toList());

        return weapons;
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

        return new ConventionalBattle(warriors, weapon, headquarters, executor);
    }

    private ExecutorService defaultBattleExecutor() {
        return newCachedThreadPool();
    }

    //-----------------------------------------------------------------------
    //WEAPON
    //-----------------------------------------------------------------------

    public Weapon weapon(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of addWeapon must not be null or empty.");
        }

        if (!weaponRegistry.hasWeapon(name)) {
            new NoSuchElementException("No addWeapon of called '" + name + "' exists.");
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
        weaponRegistry.afterWeapons();
    }
}
