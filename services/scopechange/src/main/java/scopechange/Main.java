package scopechange;

import static spark.Spark.get;
import static spark.Spark.port;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(9000);
        get("/", (request, response) -> {
            response.type("application/json");
            String sprintIdParam = request.queryParams("sprint_id");
            if (sprintIdParam == null) {
                response.type("application/json");
                response.status(HttpStatus.SC_BAD_REQUEST);
                return null; // TODO: return error object transofmed to json using response transformer
            }

            int sprintId;
            try {
                sprintId = Integer.parseInt(sprintIdParam);
            } catch (NumberFormatException ex) {
                logger.error("Sprint ID '" + sprintIdParam + "' is not an integer", ex);
                return null; // TODO: return error object transofmed to json using response transformer
            }

            return ScopeChangeCalculator.calculate(request, response, sprintId);
        });
    }
}
