package cz.jalasoft.joffensice.platoon;

import cz.jalasoft.joffensice.Platoon;

import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class TrainingCamp {

    private int recruitsCount;
    private ShootingCadence cadence;

    public TrainingCamp ofRecruits(int number) {
        this.recruitsCount = number;
        return this;
    }

    public TrainingCamp shooting(Supplier<ShootingCadence> cadenceSupplier) {
        return shooting(cadenceSupplier.get());
    }

    public TrainingCamp shooting(ShootingCadence cadence) {
        this.cadence = cadence;
        return this;
    }

    public Platoon reGroup() {

        return null;
    }
}
