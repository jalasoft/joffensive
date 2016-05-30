package cz.jalasoft.joffensive.core.weapon.definition;

import cz.jalasoft.joffensive.core.Weapon;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-30.
 */
final class IdentityWeaponDefinition implements WeaponDefinition {

    private final Weapon weapon;

    IdentityWeaponDefinition(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public Collection<Weapon> create() {
        return Collections.singleton(weapon);
    }
}
