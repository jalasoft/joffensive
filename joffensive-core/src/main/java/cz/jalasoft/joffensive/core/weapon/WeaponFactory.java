package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.weapon.proxy.WeaponProxy;
import cz.jalasoft.joffensive.core.weapon.proxy.WeaponType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-07.
 */
public final class WeaponFactory {

    public WeaponProxy newWeapon(WeaponType definition) {
        Object target = instantiateTarget(definition);
        WeaponProxy weapon = new WeaponProxy(definition, target);

        return weapon;
    }

    private Object instantiateTarget(WeaponType definition) {
        try {
            Object target = definition.type().newInstance();
            return target;
        } catch (ReflectiveOperationException exc) {
            throw new RuntimeException(exc);
        }
    }
}
