package cz.jalasoft.joffensive.core;

import cz.jalasoft.joffensive.core.weapon.definition.WeaponDefinition;

import java.time.Duration;
import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class Configuration {

    private final Duration shootTimeout;
    private final Collection<WeaponDefinition> weaponDefinitions;

    Configuration(JOffensiveConfigurer configurer) {
        this.shootTimeout = configurer.shootTimeout();
        this.weaponDefinitions = configurer.weaponDefinitions();
    }

    public Duration shootTimeout() {
        return this.shootTimeout;
    }

    public Collection<WeaponDefinition> weaponDefinitions() {
        return weaponDefinitions;
    }
}
