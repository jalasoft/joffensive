package cz.jalasoft.joffensice.platoon;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-13.
 */
public final class RegularShootingCadenceTraining implements Supplier<ShootingCadence> {

    public RegularShootingCadenceTraining withDelay(long time, TimeUnit unit) {

        return this;
    }

    public RegularShootingCadenceTraining immediatelly() {

        return this;
    }

    public RegularShootingCadenceTraining every(long time, TimeUnit unit) {

        return this;
    }

    public RegularShootingCadenceTraining just(int times) {

        return this;
    }

    public RegularShootingCadenceTraining unlimitedly() {

        return this;
    }

    public ShootingCadence get() {
        return new RegularShootingCadence();
    }
}
