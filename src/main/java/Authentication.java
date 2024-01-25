import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class Authentication {
    private static final String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();
    public static String authenticate(String username, String password) {

        // Endpoint to authenticate taiga's username and password
        String authEndpoint = TAIGA_API_ENDPOINT + "/auth";

        // Making an API call
        HttpPost request = new HttpPost(authEndpoint);
        String payload = "{\"type\":\"normal\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        request.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return parseAuthToken(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseAuthToken(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseJson);
            return rootNode.path("auth_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
