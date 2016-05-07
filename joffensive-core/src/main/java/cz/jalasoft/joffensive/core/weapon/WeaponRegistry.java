package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Weapon;

import java.util.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-30.
 */
public final class WeaponRegistry {

    private final Map<String, Weapon> weapons;
    private final Collection<WeaponDefinition> definitions;

    public WeaponRegistry() {
        this.weapons = new HashMap<>();
        this.definitions = new ArrayList<>();

    }

    public void registerWeapons(Iterable<WeaponDefinition> definitions) {
        definitions.forEach(this::registerWeapon);
    }

    public void registerWeapon(WeaponDefinition definition) {
        //TODO if exists?
    }

    public void registerWeapon(String name, Weapon weapon) {
        //TODO
    }

    public Optional<Weapon> weapon(String name) {
        Weapon weapon = weapons.get(name);

        if (weapon != null) {
            return Optional.of(weapon);
        }

        Optional<WeaponDefinition> definition = definitions
                .stream()
                .filter(def -> def.name().equals(name))
                .findAny();

        if (!definition.isPresent()) {
            return Optional.empty();
        }

        //TODO

        return null;
    }
}
