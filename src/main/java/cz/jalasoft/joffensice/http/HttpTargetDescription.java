package cz.jalasoft.joffensice.http;

import cz.jalasoft.joffensice.weapon.TargetDescription;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public final class HttpTargetDescription implements TargetDescription<HttpWeapon> {

    private final HttpWeapon parent;

    private String host;
    private int port;
    private String path;

    public HttpTargetDescription(HttpWeapon parent) {
        this.parent = parent;
    }

    public HttpTargetDescription host(String host) {
        this.host = host;
        return this;
    }

    public HttpTargetDescription port(int port) {
        this.port = port;
        return this;
    }

    public HttpTargetDescription path(String path) {
        this.path = path;
        return this;
    }


    @Override
    public HttpWeapon and() {
        parent.setTarget(this);
        return parent;
    }

    URI uri() {
        try {
            return new URIBuilder().setScheme("http").setHost(host).setPort(port).setPath(path).build();
        } catch (URISyntaxException exc) {
            throw new RuntimeException(exc);
        }
    }
}
