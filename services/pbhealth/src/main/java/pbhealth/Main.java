package pbhealth;

import static spark.Spark.get;
import static spark.Spark.port;

import bostonmodel.pbhealth.PBHealthMetrics;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(9000);
        get("/", (request, response) -> {
            String projectIdParam = request.queryParams("project_id");
            if (projectIdParam == null) {
                response.type("application/json");
                response.status(HttpStatus.SC_BAD_REQUEST);
                return ""; // TODO: return error object transofmed to json using response transformer
            }

            int projectId;
            try {
                projectId = Integer.parseInt(projectIdParam);
            } catch (NumberFormatException ex) {
                logger.error("Project ID '" + projectIdParam + "' is not an integer", ex);
                return ""; // TODO: return error object transofmed to json using response transformer
            }

            PBHealthMetrics resultMetrics = PBHealthService.calculatePBHealth(projectId);

            return "";
        });
    }
}
