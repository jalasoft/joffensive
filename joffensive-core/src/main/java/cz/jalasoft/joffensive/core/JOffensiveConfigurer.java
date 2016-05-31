package cz.jalasoft.joffensive.core;

import com.typesafe.config.Config;
import cz.jalasoft.joffensive.core.weapon.definition.WeaponDefinition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-23.
 */
public final class JOffensiveConfigurer {

    private Config externalConfig;

    Duration shootTimeout;
    Collection<WeaponDefinition> definitions;

    JOffensiveConfigurer(Config externalConfig) {
        this.externalConfig = externalConfig;

        this.shootTimeout = Duration.ofMillis(externalConfig.getLong("joffensive.shoot.timeout_millis"));
        this.definitions = new ArrayList<>();
    }

    public JOffensiveConfigurer shootTimeout(long value, TimeUnit unit) {
        long millis = unit.toMillis(value);
        shootTimeout = Duration.ofMillis(millis);

        return this;
    }

    public JOffensiveConfigurer weapon(Weapon weapon) {
        definitions.add(WeaponDefinition.byExistingWeapon(weapon));
        return this;
    }

    public JOffensiveConfigurer annotatedWeaponClass(Class<?> type) {
        definitions.add(WeaponDefinition.byAnnotatedType(type));
        return this;
    }

    public JOffensiveConfigurer scanAnnotatedWeaponClasses(String packageName) {
        definitions.add(WeaponDefinition.byAnnotatedTypesInPackage(packageName));
        return this;
    }

    public JOffensive get() {
        Configuration config = new Configuration(this);
        Collection<Weapon> weapons = createWeapons();

        return new JOffensive(config, weapons);
    }

    private Collection<Weapon> createWeapons() {
        //TODO zkontroluj duplikatni nazvy

        return definitions
                .stream()
                .map(WeaponDefinition::create)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
