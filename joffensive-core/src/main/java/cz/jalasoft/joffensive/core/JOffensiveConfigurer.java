package cz.jalasoft.joffensive.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import cz.jalasoft.joffensive.core.weapon.definition.WeaponDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class JOffensiveConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JOffensiveConfigurer.class);

    private Duration shootTimeout;
    private final Collection<WeaponDefinition> weaponDefinitions;

    JOffensiveConfigurer() {
        weaponDefinitions = new ArrayList<>();
        reloadConfiguration(ConfigFactory.load());

        LOGGER.debug("Default configuration loaded...");
    }

    public JOffensiveConfigurer loadConfigurationFrom(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("Configuration file must not be null.");
        }

        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException("Configuration file '" + file + "' must be a regular file.");
        }

        Config customConfig = ConfigFactory.parseFile(file.toFile());
        reloadConfiguration(customConfig);

        LOGGER.debug("Custom configuration overriding the default one...");

        return this;
    }

    public JOffensiveConfigurer shootTimeout(long value, TimeUnit unit) {
        if (value <= 0) {
            throw new IllegalArgumentException("Shoot timeout value must be positive and greater than zero.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit of shoot timeout must not be null.");
        }

        long millis = unit.toMillis(value);
        shootTimeout = Duration.ofMillis(millis);

        LOGGER.debug("Shoot timeout configuration set to custom value: {}{}", value, unit);

        return this;
    }

    private void reloadConfiguration(Config config) {
        shootTimeout = Duration.ofMillis(config.getLong("joffensive.shoot.timeout_millis"));
    }

    public JOffensiveConfigurer addWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon must not be null.");
        }

        weaponDefinitions.add(WeaponDefinition.byExistingWeapon(weapon));

        LOGGER.debug("Weapon '{}' has been added.", weapon.name());

        return this;
    }

    public JOffensiveConfigurer scanAnnotatedClass(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Weapon type must not be null.");
        }

        weaponDefinitions.add(WeaponDefinition.byAnnotatedType(type));

        LOGGER.debug("Type '{}' has been added for weapon annotation scan.", type.getName());

        return this;
    }

    public JOffensiveConfigurer scanAnnotatedClassesAt(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            throw new IllegalArgumentException("Package must not be null.");
        }

        weaponDefinitions.add(WeaponDefinition.byAnnotatedTypesInPackage(packageName));

        LOGGER.debug("Package '{}' for scanning weapons has been added.", packageName);

        return this;
    }

    public JOffensive get() throws WeaponsException {
        JOffensive result = new JOffensive(newConfiguration());

        return result;
    }

    private Configuration newConfiguration() {
        return new Configuration(this);
    }


    //---------------------------------------------------------------------------------
    //CONFIGURATION DATA PROVIDER
    //---------------------------------------------------------------------------------


    Duration shootTimeout() {
        return this.shootTimeout;
    }

    Collection<WeaponDefinition> weaponDefinitions() {
        return weaponDefinitions;
    }
}
