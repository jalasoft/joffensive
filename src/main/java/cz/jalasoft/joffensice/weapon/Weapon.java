package cz.jalasoft.joffensice.weapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public abstract class Weapon<W extends Weapon<W>> {

    private int bulletCount;

    public W of(int bulletCount) {
        this.bulletCount = bulletCount;
        return thisObject();
    }

    public abstract BulletDescription<W> bullets();

    public abstract TargetDescription<W> pointingAt();

    protected abstract ShootMechanism shootMechanism();

    protected abstract W thisObject();
}
