import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;

import java.util.Scanner;

public class Project {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private static String promptUser(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String promptUserPassword(String prompt) {
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword(prompt);
            return new String(passwordChars);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    public static int getProjectId(String authToken,String TAIGA_API_ENDPOINT) {

        // Prompting user to enter project slug name. A slug name is nothing but an identifier for a project.
        // Open any Taiga project and check the url of your browser. Slug name is the value after " /project/SLUG_NAME "
        // Example https://tree.taiga.io/project/SLUG_NAME/us/1?no-milestone=1

        String projectSlug = promptUser("Enter the Taiga project slug: ");
        String endpoint = TAIGA_API_ENDPOINT + "/projects/by_slug?slug=" + projectSlug;

        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);

        if (responseJson != null) {
            try {
                JsonNode projectInfo = objectMapper.readTree(responseJson);
                int projectId = projectInfo.has("id") ? projectInfo.get("id").asInt() : -1;

                if (projectId != -1) {
                    System.out.println("Project details retrieved successfully.");
                    return projectId;
                } else {
                    System.out.println("Invalid project slug. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    // Show all of the available projects to a username
    // Current issue 1/31: it is somehow getting projects I don't think I own
    public static void listAvailableProjects(String authToken, String TAIGA_API_ENDPOINT) {
        String endpoint = TAIGA_API_ENDPOINT + "/projects";

        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);

        if (responseJson != null) {
            try {
                JsonNode projectsArray = objectMapper.readTree(responseJson);
                System.out.println("Available Projects:");
                for (JsonNode projectNode : projectsArray) {
                    int projectId = projectNode.get("id").asInt();
                    String projectName = projectNode.get("name").asText();
                    String projectSlug = projectNode.get("slug").asText();
                    System.out.println("ID: " + projectId + ", Name: " + projectName + ", Slug: " + projectSlug);
                }
            } catch (Exception e) {
                System.out.println("Error when parsing the project list response.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to retrieve the list of available projects.");
        }
    }
}
