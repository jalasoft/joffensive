package cz.jalasoft.joffensive.core.weapon.annotation.introspection;


import cz.jalasoft.joffensive.core.weapon.WeaponDefinition;

import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public interface WeaponAnnotationIntrospection {

    static WeaponAnnotationIntrospection forType(Class<?> type) {
        return new WeaponClassIntrospection(type);
    }

    static WeaponAnnotationIntrospection forPackage(String packageName) {
        return new WeaponPackageIntrospection(packageName);
    }

    Collection<WeaponDefinition> introspect() throws WeaponIntrospectionException;
}
