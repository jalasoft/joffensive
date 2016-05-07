package cz.jalasoft.joffensive.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    EvenCadence(Builder builder) {
        this.afterSeconds = builder.afterSeconds;
        this.periodSeconds = builder.periodSeconds;
    }

    @Override
    public boolean shootNow(long secondsSinceStart) {
        if (afterSeconds > secondsSinceStart) {
            return false;
        }

        long sameWave = secondsSinceStart - afterSeconds;

        long shift = sameWave % periodSeconds;

        return shift == 0;
    }

    //-----------------------------------------------------
    //BUILDER
    //-----------------------------------------------------

    public static final class Builder implements Supplier<Cadence> {

        private int afterSeconds;
        private int periodSeconds;

        public Builder afterSeconds(int seconds) {
            this.afterSeconds = seconds;
            return this;
        }

        public Builder everySecond(int periodSeconds) {
            this.periodSeconds = periodSeconds;
            return this;
        }

        public Cadence get() {
            return new EvenCadence(this);
        }
    }


}
