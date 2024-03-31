package orchestrator;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import routes.Routes;

import static spark.Spark.get;
import static spark.Spark.port;

public class Orchestrator {
    private final static Logger logger = LoggerFactory.getLogger(Orchestrator.class);
    public static void start() {
        port(8000); // TODO: figure out how to make railway listen to this port (or how to get railway's port here)
        logger.info("Starting server");
        Routes.getAll().forEach(route -> {
            logger.trace("Adding " + route.getName() + " to api.");
            get("/" + route.getName(), ((request, response) -> {
                Object data = route.handleServiceRequest(request, response);
                if (data == null) {
                    response.status(HttpStatus.SC_BAD_REQUEST);
                    return "{}"; // TODO: figure out what to return for null requests
                }
                return data;
            }));
        });
    }
}
