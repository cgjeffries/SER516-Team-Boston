import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final String TAIGA_API_ENDPOINT = "https://api.taiga.io/api/v1";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String taigaUsername = promptUser("Enter your Taiga username: ");
        String taigaPassword = promptUserPassword("Enter your Taiga password: ");
        String authToken = Authentication.authenticate(taigaUsername, taigaPassword);
        if (authToken != null) {
            System.out.println("Authentication successful.");
            int projectId = Project.getProjectId(authToken,TAIGA_API_ENDPOINT);

            if (projectId != -1) {
                handleUserAction(projectId, authToken, scanner);
            }
        }
    }

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

    private static void handleUserAction(int projectId, String authToken, Scanner scanner) {
        while (true) {
            String action = promptUser(
                    "What do you want to do next?\n" +
                            "(1) Show open user stories\n" +
                            "(2) Calculate number of tasks closed per week metric\n" +
                            "(3) Calculate average lead time\n" +
                            "(4) Calculate average cycle time\n" +
                            "(5) Exit\n" +
                            "Enter action: ");

            switch (action) {
                case "1":
                    System.out.println("Getting list of all open user stories...");
                    getOpenUserStories(projectId, authToken);
                    break;

                case "2":
                    System.out.println("Calculating throughput metric...");
                    getClosedTasksPerWeek(projectId, authToken);
                    break;

                case "3":
                    System.out.println("Calculating average lead time...");
                    getLeadTime(projectId, authToken);
                    break;

                case "4":
                    System.out.println("Calculating average cycle time...");
                    getCycleTime(projectId, authToken);
                    break;

                case "5":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void getOpenUserStories(int projectId, String authToken) {
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?project=" + projectId;

        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);

        try {
            JsonNode userStoriesNode = objectMapper.readTree(responseJson);
            List<String> openUserStories = new ArrayList<>();

            for (JsonNode storyNode : userStoriesNode) {
                boolean isClosed = storyNode.has("is_closed") && storyNode.get("is_closed").asBoolean();
                if (!isClosed) {
                    String name = storyNode.has("subject") ? storyNode.get("subject").asText() : "";
                    String description = storyNode.has("description") ? storyNode.get("description").asText() : "";

                    String storyDetails = String.format("{ \"name\": \"%s\", \"description\": \"%s\" }", name, description);
                    openUserStories.add(storyDetails);
                }
            }

            String result = String.format("{ \"open_user_stories\": [%s] }", String.join(", ", openUserStories));
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String formattedJson = objectMapper.writeValueAsString(objectMapper.readTree(result));
            System.out.println(formattedJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private static void getClosedTasksPerWeek(int projectId, String authToken) {
        String endpoint = TAIGA_API_ENDPOINT + "/tasks?project=" + projectId;

        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        try {
            List<JsonNode> closedTasks = Tasks.getClosedTasks(projectId, authToken,TAIGA_API_ENDPOINT);
            List<String> taskGroups = new ArrayList<>();

            for (JsonNode task : closedTasks) {
                LocalDateTime finishedDate = parseDateTime(task.get("finished_date").asText());
                LocalDateTime weekEnding = finishedDate.plusDays(6 - finishedDate.getDayOfWeek().getValue());

                String taskDetails = String.format("{ \"weekEnding\": \"%s\", \"closedTasks\": 1 }", weekEnding);
                taskGroups.add(taskDetails);
            }

            String result = String.format("{ \"closedTasksPerWeek\": [%s] }", String.join(", ", taskGroups));
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String formattedJson = objectMapper.writeValueAsString(objectMapper.readTree(result));
            System.out.println(formattedJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getLeadTime(int projectId, String authToken) {
        List<JsonNode> closedTasks = Tasks.getClosedTasks(projectId, authToken,TAIGA_API_ENDPOINT);

        int leadTime = 0;
        int closedTasksCount = 0;

        for (JsonNode task : closedTasks) {
            LocalDateTime createdDate = parseDateTime(task.get("created_date").asText());
            LocalDateTime finishedDate = parseDateTime(task.get("finished_date").asText());

            leadTime += Duration.between(createdDate, finishedDate).toDays();
            closedTasksCount++;
        }

        double avgLeadTime = (closedTasksCount > 0) ? (double) leadTime / closedTasksCount : 0.0;
        avgLeadTime = Math.round(avgLeadTime * 100.0) / 100.0;

        System.out.println("\n***********************************\n");
        System.out.println("Average Lead Time: " + avgLeadTime);
        System.out.println("\n***********************************\n");
    }

    private static void getCycleTime(int projectId, String authToken) {
        List<JsonNode> tasks = Tasks.getClosedTasks(projectId, authToken,TAIGA_API_ENDPOINT);
        List<Integer> cycleTimeAndClosedTasks = Tasks.getTaskHistory(tasks, authToken, TAIGA_API_ENDPOINT);

        int cycleTime = cycleTimeAndClosedTasks.get(0);
        int closedTasks = cycleTimeAndClosedTasks.get(1);


        double avgCycleTime = (closedTasks != 0) ? (double) cycleTime / closedTasks : 0;
        avgCycleTime = Math.round(avgCycleTime * 100.0) / 100.0;

        System.out.println("\n***********************************\n");
        System.out.println("Average Cycle Time: " + avgCycleTime);
        System.out.println("\n***********************************\n");
    }



}
