package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.platoon.AbstractPlatoon;
import cz.jalasoft.joffensive.core.platoon.SinglePlatoon;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
final class WarriorFactory {

    Collection<Warrior> produceWarriors(Platoon platoon, Weapon weapon, Headquarters headquarters) {
        if (platoon instanceof AbstractPlatoon) {
            return produceWarriors((SinglePlatoon) platoon, weapon, headquarters);
        }

        throw new IllegalArgumentException("Platoon is on of expectected type: " + platoon.getClass().getName());
    }

    private Collection<Warrior> produceWarriors(SinglePlatoon platoon, Weapon weapon, Headquarters headquarters) {
        return range(0, platoon.size())
                .mapToObj(position -> WarriorName.from(platoon, position))
                .map(name -> new Warrior(name, weapon, platoon.skill(), headquarters))
                .collect(toList());
    }

    private Collection<Warrior> produceWarriors(AbstractPlatoon platoon) {
       return platoon.platoons()
               .stream()
               .map(this::produceWarriors)
               .flatMap(Collection::stream)
               .collect(toList());
    }
}
