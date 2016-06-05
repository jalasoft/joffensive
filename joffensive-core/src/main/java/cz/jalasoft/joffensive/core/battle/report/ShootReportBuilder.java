package cz.jalasoft.joffensive.core.battle.report;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.battle.warrior.WarriorName;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-05.
 */
public final class ShootReportBuilder {

    Long startingAt;
    Long endingAt;

    ShootReportBuilder() {}

    public ShootReportBuilder fromNow() {

        return this;
    }

    public ShootReportBuilder untilNow() {

        return this;
    }

    public ShootReportBuilder byWarrior(WarriorName warrior) {

        return this;
    }

    public ShootReportBuilder recoil(Recoil recoil) {

        return this;
    }

    public ShootReport get() {
        return new ShootReport(this);
    }
}
