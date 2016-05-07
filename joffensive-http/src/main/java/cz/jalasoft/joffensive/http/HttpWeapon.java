package cz.jalasoft.joffensive.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public final class HttpWeapon implements Weapon {

    public static HttpWeaponBuilder newWeapon() {
        return new HttpWeaponBuilder();
    }

    //---------------------------------------------------------------
    //INSTANCE SCOPE
    //---------------------------------------------------------------


    private final String uri;
    private final HttpWeaponBuilder.Method method;
    private final CloseableHttpClient httpClient;

    HttpWeapon(HttpWeaponBuilder builder) {
        this.uri = builder.uri;
        this.method = builder.method;
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public Recoil shoot() {
        long startTimeMillis = System.currentTimeMillis();
        long endTimeMillis;
        Recoil.Type type;

        try {
            boolean result = shootSafely();
            type = result ? Recoil.Type.SUCCESS : Recoil.Type.FAILURE;
        } catch(IOException exc) {
            type = Recoil.Type.FAILURE;
        } finally {
            endTimeMillis = System.currentTimeMillis();
        }

        return Recoil.result(type)
                .lastingMillis(endTimeMillis-startTimeMillis)
                .get();
    }

    private boolean shootSafely() throws IOException {
        HttpGet request = new HttpGet(uri);

        try(CloseableHttpResponse response =  httpClient.execute(request)) {
            int status = response.getStatusLine().getStatusCode();

            if (status != 200) {
                return false;
            }
        }
        return true;
    }
}
