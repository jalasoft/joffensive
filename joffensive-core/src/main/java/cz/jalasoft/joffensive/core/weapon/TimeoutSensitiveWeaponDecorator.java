package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class TimeoutSensitiveWeaponDecorator implements Weapon {

    private final Weapon weapon;
    private final Duration timeout;
    private final Executor executor;

    public TimeoutSensitiveWeaponDecorator(Weapon weapon, Duration timeout, Executor executor) {
        this.weapon = weapon;
        this.timeout = timeout;
        this.executor = executor;
    }

    @Override
    public Recoil shoot() throws Exception {

        Future<Recoil> future = CompletableFuture.supplyAsync(supplier(), executor);
        try {
            Recoil result = future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return result;
        } catch (ExecutionException exc) {
            Throwable cause = exc.getCause();

            if (cause instanceof ShootException) {
                throw (Exception) cause.getCause();
            }

            throw exc;
        }
    }

    private Supplier<Recoil> supplier() {
        return () -> {
            try {
                return weapon.shoot();
            } catch (Exception exc) {
                throw new ShootException(exc);
            }
        };
    }

    //------------------------------------------------------------------------------------
    //INTERNAL EXCEPTION TO DISTINGUISH WEAPON EXCEPTION
    //------------------------------------------------------------------------------------

    private static final class ShootException extends RuntimeException {

        ShootException(Exception exc) {
            super(exc);
        }
    }
}
