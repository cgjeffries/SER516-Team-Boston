import static spark.Spark.get;
import static spark.Spark.port;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taiga.TaigaClient;
import taiga.models.sprint.UserStoryDetail;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(9000);
        get("/", (request, response) -> {
            String sprintIdParam = request.queryParams("sprint_id");
            if (sprintIdParam == null) {
                response.type("application/json");
                response.status(HttpStatus.SC_BAD_REQUEST);
                return ""; // TODO: return error object transofmed to json using response transformer
            }

            int sprintId;
            try {
                sprintId = Integer.parseInt(sprintIdParam);
            } catch (NumberFormatException ex) {
                logger.error("Sprint ID '" + sprintIdParam + "' is not an integer", ex);
                return ""; // TODO: return error object transofmed to json using response transformer
            }

            AtomicReference<List<UserStoryDetail>> stories = new AtomicReference<>(); 
            TaigaClient.getUserStoryAPI().listMilestoneUserStories(sprintId, result -> {
                if (result.getStatus() == HttpStatus.SC_OK) {
                    stories.set(List.of(result.getContent()));
                }
            }).join();
            if (stories.get() == null) {
                response.status(HttpStatus.SC_NOT_FOUND);
                response.type("application/json");
                return ""; // TODO: return error object transofmed to json using response transformer
            }

            return "";
        });
    }
}
