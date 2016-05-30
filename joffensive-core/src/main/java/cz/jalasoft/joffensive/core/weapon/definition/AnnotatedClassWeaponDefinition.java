package cz.jalasoft.joffensive.core.weapon.definition;

import cz.jalasoft.joffensive.core.weapon.proxy.WeaponType;
import cz.jalasoft.joffensive.core.weapon.introspect.WeaponIntrospectionException;

import java.util.Collection;

import static cz.jalasoft.joffensive.core.weapon.introspect.WeaponAnnotationIntrospection.forType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-30.
 */
final class AnnotatedClassWeaponDefinition extends IntrospectedWeaponDefinition implements WeaponDefinition {

    private final Class<?> type;

    AnnotatedClassWeaponDefinition(Class<?> type) {
        this.type = type;
    }

    @Override
    Collection<WeaponType> introspect() throws WeaponIntrospectionException {
        return forType(type)
                .introspect();
    }
}
