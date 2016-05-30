package cz.jalasoft.joffensive.core.weapon.definition;

import cz.jalasoft.joffensive.core.Weapon;

import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-30.
 */
public interface WeaponDefinition {

    static WeaponDefinition byAnnotatedType(Class<?> type) {
        return new AnnotatedClassWeaponDefinition(type);
    }

    static WeaponDefinition byAnnotatedTypesInPackage(String packageName) {
        return new WeaponsInPackageDefinition(packageName);
    }

    static WeaponDefinition byExistingWeapon(Weapon weapon) {
        return new IdentityWeaponDefinition(weapon);
    }

    Collection<Weapon> create();
}
