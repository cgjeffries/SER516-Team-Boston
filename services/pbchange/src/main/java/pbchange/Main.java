package pbchange;

import static spark.Spark.get;
import static spark.Spark.port;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bostonmodel.util.JsonTransformer;
import serviceutil.Env;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(Env.getPort());
        get("/pbchange", (request, response) -> {
            int sprintId;
            try {
                sprintId = Integer.parseInt(request.queryParams("sprint_id"));
            } catch (NumberFormatException ex) {
                logger.error("Sprint ID '" + request.queryParams("sprint_id") + "' is not an integer", ex);
                response.status(HttpStatus.BAD_REQUEST_400);
                return "";
            }

            return PBChangeCalculator.calculate(response, sprintId);
        }, new JsonTransformer());
    }
}
