package cz.jalasoft.joffensive.core;

import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public final class EvenCadence implements Cadence {

    public static Builder evenly() {
        return new Builder();
    }

    //--------------------------------------------------------
    //INSTANCE SCOPE
    //--------------------------------------------------------

    private final long afterSeconds;
    private final long periodSeconds;
    private final long times;

    private long actualTimes;

    EvenCadence(Builder builder) {
        this.afterSeconds = builder.afterSeconds;
        this.periodSeconds = builder.periodSeconds;
        this.times = builder.times;

        this.actualTimes = 0;
    }

    @Override
    public boolean canShootNow(long secondsSinceStart) {
        if (afterSeconds > secondsSinceStart) {
            return false;
        }

        if (isFinished()) {
            return false;
        }

        long sameWave = secondsSinceStart - afterSeconds;

        long shift = sameWave % periodSeconds;

        boolean result = shift == 0;

        if (result) {
            actualTimes++;
        }

        return result;
    }

    @Override
    public boolean isFinished() {
        return actualTimes >= times;
    }

    //-----------------------------------------------------
    //BUILDER
    //-----------------------------------------------------

    public static final class Builder implements Supplier<Cadence> {

        private long afterSeconds = 0;
        private long periodSeconds = 1;
        private long times = Long.MAX_VALUE;

        public Builder afterSeconds(int seconds) {
            this.afterSeconds = seconds;
            return this;
        }

        public Builder everySecond(int periodSeconds) {
            this.periodSeconds = periodSeconds;
            return this;
        }

        public Builder times(int times) {
            this.times = times;
            return this;
        }

        public Cadence get() {
            return new EvenCadence(this);
        }
    }


}
