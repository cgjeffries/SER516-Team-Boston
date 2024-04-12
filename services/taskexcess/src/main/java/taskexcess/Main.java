package taskexcess;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bostonmodel.util.JsonTransformer;
import serviceutil.Env;
import static spark.Spark.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(Env.getPort());
        get("/taskexcess", (request, response) -> {
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

            return TaskExcessCalculator.calculate(request, response, sprintId);
        }, new JsonTransformer());
    }
}
