package cz.jalasoft.joffensive.core.battle.report;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ShootReport {

    public static ShootReportBuilder report() {
        return new ShootReportBuilder();
    }

    //----------------------------------------------------------
    //INSTANCE SCOPE
    //----------------------------------------------------------

    //private final WarriorName warrior;
    //private final Recoil recoil;

    ShootReport(ShootReportBuilder builder) {
        //this.warrior = builder.
        //this.recoil = recoil;
    }
}

