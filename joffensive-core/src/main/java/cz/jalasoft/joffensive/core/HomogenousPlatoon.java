package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-25.
 */
public final class HomogenousPlatoon implements Platoon {

    private final String name;
    private final int size;
    private final Cadence cadence;
    private final int magazineSize;

    HomogenousPlatoon(TrainingCamp camp) {
        this.name = camp.name;
        this.size = camp.recruitsCount;
        this.cadence = camp.cadence;
        this.magazineSize = camp.magazineSize;
    }

    @Override
    public void fire(Weapon weapon) {

    }

    @Override
    public Platoon regroup(Platoon recruits) {
        return null;
    }
}
