package cz.jalasoft.joffensice.platoon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-14.
 */
public interface ShootingCadence {

    boolean shootNow(long secondsSinceStart);
}
