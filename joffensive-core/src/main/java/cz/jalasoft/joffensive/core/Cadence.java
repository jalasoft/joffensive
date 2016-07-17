package cz.jalasoft.joffensive.core;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public interface Cadence {

    boolean canShootNow(long secondsSinceStart);

    boolean isFinished();
}
