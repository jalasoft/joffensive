package cz.jalasoft.joffensive.test.weapon;

import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.weapon.TimeoutSensitiveWeaponDecorator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public class TimeoutSensitiveWeaponDecoratorTest {

    @Test(expectedExceptions = TimeoutException.class)
    public void weaponSleepingTooLongThrowsTimeoutException() throws Exception {

        Weapon w  = new TimeoutSensitiveWeaponDecorator(sleepingWeapon(2000), Duration.ofSeconds(1));
        w.shoot();
    }

    @Test
    public void weaponSleepingNotTooLongDoesNotThrowTimeoutException() throws Exception {

        Weapon w = new TimeoutSensitiveWeaponDecorator(sleepingWeapon(2000), Duration.ofMillis(3000));
        w.shoot();
    }

    @Test(expectedExceptions = MyException.class)
    public void weaponThrowingExceptionPropagatesTheExceptionUpTheCallStack() throws Exception {

        Weapon w = new TimeoutSensitiveWeaponDecorator(throwing(() -> new MyException()), Duration.ofSeconds(3000));
        w.shoot();
    }

    private Weapon sleepingWeapon(long millis) {
        return () -> {

            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException exc) {
            }
            return null;
        };
    }

    private Weapon throwing(Supplier<Exception> supplier) {
        return () -> {
            throw supplier.get();
        };
    }

    private static final class MyException extends Exception {

    }
}
