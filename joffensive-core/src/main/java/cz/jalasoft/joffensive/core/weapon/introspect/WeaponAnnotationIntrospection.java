package cz.jalasoft.joffensive.core.weapon.introspect;


import cz.jalasoft.joffensive.core.weapon.proxy.WeaponType;

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

    Collection<WeaponType> introspect() throws WeaponIntrospectionException;
}
