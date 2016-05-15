package cz.jalasoft.joffensive.core;

import java.util.concurrent.ExecutorService;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public interface Platoon {

    Battle fire(Weapon weapon);

    Battle fire(Weapon weapon, ExecutorService executor);

    Platoon regroup(Platoon other);
}
