package cz.jalasoft.joffensive.core.platoon;

import cz.jalasoft.joffensive.core.Cadence;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-12.
 */
public final class Skill {

    private final Cadence candence;
    private final int magazineSize;

    public Skill(Cadence candence, int magazineSize) {
        this.candence = candence;
        this.magazineSize = magazineSize;
    }

    public Cadence cadence() {
        return this.candence;
    }

    public int magazineSize() {
        return magazineSize;
    }

    //TODO
}
