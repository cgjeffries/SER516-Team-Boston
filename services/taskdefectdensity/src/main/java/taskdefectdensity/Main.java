package taskdefectdensity;

import bostonmodel.util.JsonTransformer;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serviceutil.Env;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(Env.getPort());
        get("/taskdefectdensity", (request, response) -> {
            response.type("application/json");
            String sprintIdParam = request.queryParams("sprint_id");
            if (sprintIdParam == null) {
                response.type("application/json");
                response.status(HttpStatus.SC_BAD_REQUEST);
                return "";
            }

            int sprintId;
            try {
                sprintId = Integer.parseInt(sprintIdParam);
            } catch (NumberFormatException ex) {
                logger.error("Sprint ID '" + sprintIdParam + "' is not an integer", ex);
                return "";
            }

            return TaskDefectDensityCalculator.calculate(request, response, sprintId);
        }, new JsonTransformer());
    }
}

