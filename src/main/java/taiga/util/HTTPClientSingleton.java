package taiga.util;

import java.net.http.HttpClient;
import java.time.Duration;

public class HTTPClientSingleton {
    private static HttpClient client;

    /**
     * gets the shared instance of HTTPClient, should in theory be faster?
     *
     * @return The shared HTTPClient
     */
    public static HttpClient getInstance() {
        if (client == null) {
            client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
        }
        return client;
    }
}
