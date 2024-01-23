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

public class Tasks {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static List<JsonNode> getClosedTasks(int projectId, String authToken, String TAIGA_API_ENDPOINT) {
        String endpoint = TAIGA_API_ENDPOINT + "/tasks?project=" + projectId;

        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        String responseJson = HTTPRequest.sendHttpRequest(request);

        try {
            JsonNode tasksNode = objectMapper.readTree(responseJson);
            List<JsonNode> closedTasks = new ArrayList<>();

            for (JsonNode taskNode : tasksNode) {
                boolean isClosed = taskNode.has("is_closed") && taskNode.get("is_closed").asBoolean();
                if (isClosed) {
                    closedTasks.add(taskNode);
                }
            }

            return closedTasks;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private static int[] calculateCycleTime(JsonNode historyData, LocalDateTime finishedDate) {
        int cycleTime = 0;
        int closedTasks = 0;

        for (JsonNode event : historyData) {
            JsonNode valuesDiff = event.get("values_diff");
            if (valuesDiff != null && valuesDiff.has("status")) {
                JsonNode statusDiff = valuesDiff.get("status");
                if (statusDiff.isArray() && statusDiff.size() == 2
                        && "New".equals(statusDiff.get(0).asText()) && "In progress".equals(statusDiff.get(1).asText())) {
                    LocalDateTime createdAt =parseDateTime(event.get("created_at").asText());
                    cycleTime += Duration.between(createdAt.toLocalDate().atStartOfDay(), finishedDate.toLocalDate().atStartOfDay()).toDays();
                    closedTasks++;
                }
            }
        }

        return new int[]{cycleTime, closedTasks};
    }

    public static List<Integer> getTaskHistory(List<JsonNode> tasks, String authToken, String TAIGA_API_ENDPOINT) {
        List<Integer> result = new ArrayList<>(List.of(0, 0));

        for (JsonNode task : tasks) {
            int taskId = task.get("id").asInt();
            String taskHistoryUrl = TAIGA_API_ENDPOINT + "/history/task/" + taskId;

            try {
                HttpGet request = new HttpGet(taskHistoryUrl);
                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
                request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                String responseJson = HTTPRequest.sendHttpRequest(request);

                JsonNode historyData = objectMapper.readTree(responseJson);
                LocalDateTime finishedDate = parseDateTime(task.get("finished_date").asText());

                int[] cycleTimeAndClosedTasks = calculateCycleTime(historyData, finishedDate);
                result.set(0, result.get(0) + cycleTimeAndClosedTasks[0]);
                result.set(1, result.get(1) + cycleTimeAndClosedTasks[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
