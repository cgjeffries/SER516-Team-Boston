package taskchurn;

import bostonmodel.util.JsonTransformer;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serviceutil.Env;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(Env.getPort());
        get("/taskchurn", (request, response) -> {
            response.type("application/json");

            int sprintId;

            try {
                sprintId = Integer.parseInt(request.queryParams("sprint_id"));
            } catch (NumberFormatException ex) {
                response.status(HttpStatus.SC_BAD_REQUEST);
                logger.error("sprint_id must be an integer");
                return "";
            }

            return TaskChurnCalculator.calculate(response, sprintId);
        }, new JsonTransformer());
    }
}
