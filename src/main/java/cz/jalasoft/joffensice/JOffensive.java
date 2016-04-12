package cz.jalasoft.joffensice;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public final class JOffensive {


    public static PlatoonTraining training() {

    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        Platoon platoon1 = JOffensive.training().ofWarriors(23).shootingEvery(2, TimeUnit.SECONDS).drill();
        Platoon platoon2 = JOffensive.training().ofWarriors(11).shootingEvery(5, TimeUnit.SECONDS).drill();

        Platoon platoon = platoon1.form(platoon2);

        try(CloseableHttpClient client = HttpClients.createDefault()) {

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("vodnisvetkolin-jalasoft.rhcloud.com")
                    .setPort(80)
                    .setPath("status")
                    .build();

            HttpGet get = new HttpGet(uri);

            try(CloseableHttpResponse response = client.execute(get)) {

                HttpEntity entity = response.getEntity();
                String text = EntityUtils.toString(entity);

                System.out.println(text);
            }
        } catch (IOException exc) {
            throw exc;
        }

    }

    private JOffensive() {
        throw new RuntimeException("Cannot instantiate");
    }
}
