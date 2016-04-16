package cz.jalasoft.joffensice.http;

import cz.jalasoft.joffensice.weapon.ShootMechanism;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public final class HttpShootMechanism implements ShootMechanism {

    private final HttpTargetDescription target;
    private final HttpBulletDescription bullets;

    HttpShootMechanism(HttpTargetDescription target, HttpBulletDescription bullets) {
        this.target = target;
        this.bullets = bullets;
    }

    @Override
    public void shoot() {

    }
}
