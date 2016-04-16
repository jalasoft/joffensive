package cz.jalasoft.joffensice.platoon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-14.
 */
public final class RegularShootingCadence implements ShootingCadence {

    private final long delaySeconds;
    private final long periodSeconds;

    public RegularShootingCadence(long delaySeconds, long periodSeconds) {
        this.delaySeconds = delaySeconds;
        this.periodSeconds = periodSeconds;
    }

    @Override
    public boolean shootNow(long secondsSinceStart) {
        if (delaySeconds == secondsSinceStart) {
            return true;
        }

        long sameWave = secondsSinceStart - delaySeconds;

        long shift = secondsSinceStart % periodSeconds;

        return shift == 0;
    }
}
