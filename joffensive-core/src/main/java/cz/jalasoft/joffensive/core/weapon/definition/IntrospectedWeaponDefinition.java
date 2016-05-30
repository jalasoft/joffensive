package cz.jalasoft.joffensive.core.weapon.definition;

import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.weapon.proxy.WeaponProxy;
import cz.jalasoft.joffensive.core.weapon.proxy.WeaponType;
import cz.jalasoft.joffensive.core.weapon.introspect.WeaponIntrospectionException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-30.
 */
abstract class IntrospectedWeaponDefinition implements WeaponDefinition {

    abstract Collection<WeaponType> introspect() throws WeaponIntrospectionException;

    @Override
    public final Collection<Weapon> create() {
        try {
            return introspect()
                    .stream()
                    .map(this::newWeapon)
                    .collect(Collectors.toList());
        } catch (WeaponIntrospectionException exc) {
            throw new RuntimeException(exc);
        }
    }

    private Weapon newWeapon(WeaponType definition) {
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
