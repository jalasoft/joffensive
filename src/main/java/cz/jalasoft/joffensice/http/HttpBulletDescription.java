package cz.jalasoft.joffensice.http;

import cz.jalasoft.joffensice.weapon.BulletDescription;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public final  class HttpBulletDescription implements BulletDescription<HttpWeapon> {

    private final HttpWeapon parent;

    HttpBulletDescription(HttpWeapon parent) {
        this.parent = parent;
    }

    @Override
    public HttpWeapon and() {
        parent.setBullets(this);
        return parent;
    }
}
