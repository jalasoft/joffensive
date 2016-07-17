package cz.jalasoft.joffensive.core.battle;

import cz.jalasoft.joffensive.core.Battle;
import cz.jalasoft.joffensive.core.Platoon;
import cz.jalasoft.joffensive.core.Weapon;

import java.util.concurrent.ExecutorService;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-12.
 */
public interface BattleBootstrap {

    Battle newBattle(Platoon platoon, Weapon weapon, ExecutorService executor);
}
