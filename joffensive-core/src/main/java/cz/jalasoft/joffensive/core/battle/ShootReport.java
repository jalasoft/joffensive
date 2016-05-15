package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Recoil;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class ShootReport {

    private final WarriorName warrior;
    private final Recoil recoil;

    public ShootReport(WarriorName warrior, Recoil recoil) {
        this.warrior = warrior;
        this.recoil = recoil;
    }
}
