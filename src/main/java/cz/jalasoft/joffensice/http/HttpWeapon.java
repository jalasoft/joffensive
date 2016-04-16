package cz.jalasoft.joffensice.http;

import cz.jalasoft.joffensice.weapon.Weapon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public final class HttpWeapon extends Weapon<HttpWeapon> {

    private HttpTargetDescription target;
    private HttpBulletDescription bullets;

    @Override
    public HttpTargetDescription pointingAt() {
        return new HttpTargetDescription(this);
    }

    void setTarget(HttpTargetDescription target) {
        this.target = target;
    }

    @Override
    public HttpBulletDescription bullets() {
        return new HttpBulletDescription(this);
    }

    void setBullets(HttpBulletDescription bullets) {
        this.bullets = bullets;
    }

    @Override
    protected HttpShootMechanism shootMechanism() {
        return new HttpShootMechanism(target, bullets);
    }

    @Override
    protected HttpWeapon thisObject() {
        return this;
    }
}
