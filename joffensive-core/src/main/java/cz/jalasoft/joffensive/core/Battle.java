package cz.jalasoft.joffensive.core;

import cz.jalasoft.joffensive.core.battle.BattleObserver;

import java.time.Duration;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-12.
 */
public interface Battle {

    void fire();

    void ceaseFire();

    void addObserver(BattleObserver observer, Duration notificationInterval);

    void removeObserver(BattleObserver observer);
}
