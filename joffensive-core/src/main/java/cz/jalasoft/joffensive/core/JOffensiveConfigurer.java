package cz.jalasoft.joffensive.core;

import com.typesafe.config.Config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class JOffensiveConfigurer {

    private Config externalConfig;

    Duration shootTimeout;

    JOffensiveConfigurer(Config externalConfig) {
        this.externalConfig = externalConfig;

        this.shootTimeout = Duration.ofMillis(externalConfig.getLong("joffensive.shoot.timeout_millis"));
    }

    public JOffensiveConfigurer shootTimeout(long value, TimeUnit unit) {
        long millis = unit.toMillis(value);
        shootTimeout = Duration.ofMillis(millis);

        return this;
    }

    public JOffensive get() {
        Configuration config = new Configuration(this);
        return new JOffensive(config);
    }
}
