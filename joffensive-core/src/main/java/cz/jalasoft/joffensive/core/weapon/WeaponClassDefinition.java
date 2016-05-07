package cz.jalasoft.joffensive.core.weapon;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static java.util.Collections.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-29.
 */
public final class WeaponClassDefinition {

    public static Builder on(Class<?> type) {
        return new Builder(type);
    }

    //----------------------------------------------------------------
    //INSTANCE SCOPE
    //----------------------------------------------------------------

    private final Class<?> type;
    private final Map<String, Method> shootMethods;
    private final Collection<Method> prepareWeaponMethods;
    private final Collection<Method> cleanWeaponMethods;

    private WeaponClassDefinition(Builder builder) {
        this.type = builder.type;
        this.shootMethods = builder.shootMethods;
        this.prepareWeaponMethods = builder.prepareMethods;
        this.cleanWeaponMethods = builder.cleanMethods;
    }

    public Class<?> type() {
        return type;
    }

    public Iterable<Method> prepareWeaponMethods() {
        return unmodifiableCollection(prepareWeaponMethods);
    }

    public Iterable<Method> cleanWeaponMethods() {
        return unmodifiableCollection(cleanWeaponMethods);
    }

    //----------------------------------------------------------------
    //BUILDER
    //----------------------------------------------------------------

    public static final class Builder {

        final Class<?> type;
        Map<String, Method> shootMethods;
        Collection<Method> prepareMethods;
        Collection<Method> cleanMethods;

        Builder(Class<?> type) {
            this.type = type;
        }

        public Builder withShootMethod(String name, Method method) {
            this.shootMethods.put(name, method);
            return this;
        }

        public Builder withPrepareMethods(Collection<Method> methods) {
            this.prepareMethods = methods;
            return this;
        }

        public Builder withCleanMethod(Collection<Method> methods) {
            this.cleanMethods = methods;
            return this;
        }

        public WeaponClassDefinition get() {
            return new WeaponClassDefinition(this);
        }
    }
}
