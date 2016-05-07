package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-25.
 */
public final class RegroupedPlatoon implements Platoon {

    private final Platoon group1;
    private final Platoon group2;

    public RegroupedPlatoon(Platoon group1, Platoon group2) {
        this.group1 = group1;
        this.group2 = group2;
    }

    @Override
    public void fire(Weapon weapon) {

    }

    @Override
    public Platoon regroup(Platoon recruits) {
        return null;
    }
}
