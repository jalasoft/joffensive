package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.warrior.Warrior;
import cz.jalasoft.joffensive.core.warrior.WarriorFactory;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-12.
 */
public final class BattleBootstrap {

    private final WarriorFactory warriorFactory;

    public BattleBootstrap() {
        warriorFactory = new WarriorFactory();
    }

    public Battle initiate(Platoon platoon, Weapon weapon, ExecutorService executor) {
        Headquarters headquarters = new Headquarters();
        Collection<Warrior> warriors = warriorFactory.produceWarriors(platoon, weapon, headquarters);

        return new ConventionalBattle(warriors, headquarters, executor);
    }
}
