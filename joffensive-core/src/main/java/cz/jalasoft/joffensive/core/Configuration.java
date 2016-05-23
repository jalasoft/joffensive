package cz.jalasoft.joffensive.core;

import java.time.Duration;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class Configuration {

    private final Duration shootTimeout;

    Configuration(JOffensiveConfigurer configurer) {
        this.shootTimeout = configurer.shootTimeout;
    }

    public Duration shootTimeoutValue() {
        return this.shootTimeout;
    }
}
