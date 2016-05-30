package cz.jalasoft.joffensive.core.weapon.definition;

import cz.jalasoft.joffensive.core.weapon.introspect.WeaponIntrospectionException;
import cz.jalasoft.joffensive.core.weapon.proxy.WeaponType;

import java.util.Collection;

import static cz.jalasoft.joffensive.core.weapon.introspect.WeaponAnnotationIntrospection.forPackage;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-30.
 */
final class WeaponsInPackageDefinition extends IntrospectedWeaponDefinition implements WeaponDefinition {

    private final String packageName;

    WeaponsInPackageDefinition(String packageName) {
        this.packageName = packageName;
    }

    @Override
    Collection<WeaponType> introspect() throws WeaponIntrospectionException {
        return forPackage(packageName)
                .introspect();
    }
}
