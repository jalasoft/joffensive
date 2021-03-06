package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.battle.report.ShootReport;

import java.util.concurrent.CountDownLatch;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class Headquarters {

    private volatile CountDownLatch startingLatch;

    public Headquarters() {
        this.startingLatch = new CountDownLatch(1);
    }

    public CountDownLatch startingLatch() {
        return startingLatch;
    }

    public void warriorReportShoot(ShootReport report) {

    }
}
