package cz.jalasoft.joffensive.core.battle.warrior;

import java.util.concurrent.CountDownLatch;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-28.
 */
final class StartingWarriorDecorator implements Warrior {

    private final Warrior decorated;
    private final CountDownLatch startingLatch;

    StartingWarriorDecorator(Warrior decorated, CountDownLatch startingLatch) {
        this.decorated = decorated;
        this.startingLatch = startingLatch;
    }

    @Override
    public WarriorName name() {
        return decorated.name();
    }

    @Override
    public void fight() {
        if (await()) {
            return;
        }
        decorated.fight();
    }

    private boolean await() {
        try {
            startingLatch.await();
            return false;
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
            return true;
        }
    }
}
