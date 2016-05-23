package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.Configuration;
import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.warrior.Warrior;
import cz.jalasoft.joffensive.core.battle.warrior.WarriorFactory;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-12.
 */
public final class BattleBootstrap {

    private final Configuration configuration;
    private final WarriorFactory warriorFactory;

    public BattleBootstrap(Configuration configuration) {
        this.configuration = configuration;
        this.warriorFactory = new WarriorFactory();
    }

    public Battle initiate(Platoon platoon, Weapon weapon, ExecutorService executor) {
        Headquarters headquarters = new Headquarters();
        Collection<Warrior> warriors = warriorFactory.produceWarriors(platoon, weapon, headquarters);

        return new ConventionalBattle(warriors, headquarters, executor);
    }
}
