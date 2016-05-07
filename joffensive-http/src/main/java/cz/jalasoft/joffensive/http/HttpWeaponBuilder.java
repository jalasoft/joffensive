package cz.jalasoft.joffensive.http;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public final class HttpWeaponBuilder {

    public enum Method {
        GET
    }

    Method method;
    String uri;

    public HttpWeaponBuilder target(String uri) {
        this.uri = uri;
        return this;
    }

    public HttpWeaponBuilder methodGet() {
        this.method = Method.GET;
        return this;
    }

    public HttpWeaponBuilder withHeader(String key, String value) {

        return this;
    }

    public HttpWeapon get() {
        return new HttpWeapon(this);
    }
}
