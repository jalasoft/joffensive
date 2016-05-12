package cz.jalasoft.joffensive.core;

import java.util.concurrent.Executor;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public interface Platoon {

    Battle fire(Weapon weapon);

    Battle fire(Weapon weapon, Executor executor);

    Platoon regroup(Platoon other);
}
