package cz.jalasoft.joffensive.core.warrior;

import java.util.concurrent.Callable;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-16.
 */
public interface Warrior extends Callable<Void> {

    WarriorName name();

    void fight();

    @Override
    default Void call() {
        fight();
        return null;
    }
}
