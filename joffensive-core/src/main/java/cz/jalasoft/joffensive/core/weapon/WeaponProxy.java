package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.weapon.invokable.Invokable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public final class WeaponProxy implements Weapon {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponProxy.class);

    private final WeaponDefinition definition;
    private final Object target;

    WeaponProxy(WeaponDefinition definition, Object target) {
        this.definition = definition;
        this.target = target;
    }

    @Override
    public void beforeWeapon() throws Exception {
        invokeSafely(definition.beforeWeapon());
    }

    @Override
    public void beforeShoot() throws Exception {
        invokeSafely(definition.beforeShoot());
    }

    @Override
    public Recoil shoot() throws Exception {
        return invokeSafely(definition.shoot());
    }

    @Override
    public void afterShoot() throws Exception {
        invokeSafely(definition.afterShoot());
    }

    @Override
    public void afterWeapon() throws Exception {
        invokeSafely(definition.afterWeapon());
    }

    private <T> T invokeSafely(Invokable<T> invokable) throws Exception {
        try {
            return invokable.invoke(target);
        } catch (InvocationTargetException exc) {
            throw translatedException(exc);
        }
    }

    private Exception translatedException(InvocationTargetException exc) {
        Throwable cause = exc.getCause();
        if (cause instanceof Error) {
            return new RuntimeException(cause);
        }
        return (Exception) cause;
    }

    public String name() {
        return definition.name();
    }
}
