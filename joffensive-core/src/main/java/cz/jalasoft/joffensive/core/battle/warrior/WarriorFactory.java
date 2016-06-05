package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.Configuration;
import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.Headquarters;
import cz.jalasoft.joffensive.core.platoon.AbstractPlatoon;
import cz.jalasoft.joffensive.core.platoon.SinglePlatoon;
import cz.jalasoft.joffensive.core.platoon.Skill;
import cz.jalasoft.joffensive.core.weapon.TimeoutSensitiveWeaponDecorator;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class WarriorFactory {

    private final Configuration config;

    public WarriorFactory(Configuration config) {
        this.config = config;
    }

    public Collection<Warrior> produceWarriors(Platoon platoon, Weapon weapon, Headquarters headquarters) {
        if (platoon instanceof AbstractPlatoon) {
            return produceWarriors((SinglePlatoon) platoon, weapon, config, headquarters);
        }

        throw new IllegalArgumentException("Platoon is of an unexpected type: " + platoon.getClass().getName());
    }

    private Collection<Warrior> produceWarriors(SinglePlatoon platoon, Weapon weapon, Configuration config, Headquarters headquarters) {
        return range(0, platoon.size())
                .mapToObj(position -> WarriorName.from(platoon, position))
                .map(name -> newWarrior(name, weapon, platoon.skill(), headquarters))
                .collect(toList());
    }

    private Collection<Warrior> produceWarriors(AbstractPlatoon platoon) {
       return platoon.platoons()
               .stream()
               .map(this::produceWarriors)
               .flatMap(Collection::stream)
               .collect(toList());
    }

    private Warrior newWarrior(WarriorName name, Weapon weapon, Skill skill, Headquarters headquarters) {

        Weapon timeoutSensitiveWeapon = new TimeoutSensitiveWeaponDecorator(weapon, config.shootTimeoutValue());

        Warrior reportingWarrior = new ShootingAndReportingWarrior(name, timeoutSensitiveWeapon, headquarters::warriorReportShoot);
        Warrior shootingWarrior = new MagazineShootingWarriorDecorator(reportingWarrior, skill);
        Warrior timingWarrior = new CadenceDrivingWarriorDecorator(shootingWarrior, skill);
        Warrior startingWarrior = new StartingWarriorDecorator(timingWarrior, headquarters.startingLatch());

        return startingWarrior;
    }
}
