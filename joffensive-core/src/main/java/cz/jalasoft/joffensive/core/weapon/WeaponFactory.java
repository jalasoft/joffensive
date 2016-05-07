package cz.jalasoft.joffensive.core.weapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-07.
 */
public final class WeaponFactory {

    public WeaponProxy newWeapon(WeaponDefinition definition) {
        Object target = instantiateTarget(definition);
        return new WeaponProxy(definition, target);
    }

    private Object instantiateTarget(WeaponDefinition definition) {
        try {
            Object target = definition.type().newInstance();
            return target;
        } catch (ReflectiveOperationException exc) {
            throw new RuntimeException(exc);
        }
    }
}
