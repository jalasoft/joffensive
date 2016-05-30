package cz.jalasoft.joffensive.core.weapon.proxy;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.weapon.proxy.invokable.Invokable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public final class WeaponProxy implements Weapon {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeaponProxy.class);

    private final WeaponType weaponType;
    private final Object target;

    public WeaponProxy(WeaponType type, Object target) {
        this.weaponType = type;
        this.target = target;
    }

    @Override
    public void beforeWeapon() throws Exception {
        invokeSafely(weaponType.beforeWeapon());
    }

    @Override
    public void beforeShoot() throws Exception {
        invokeSafely(weaponType.beforeShoot());
    }

    @Override
    public Recoil shoot() throws Exception {
        return invokeSafely(weaponType.shoot());
    }

    @Override
    public void afterShoot() throws Exception {
        invokeSafely(weaponType.afterShoot());
    }

    @Override
    public void afterWeapon() throws Exception {
        invokeSafely(weaponType.afterWeapon());
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
        return weaponType.name();
    }
}
