package router;

import bostonmodel.util.JsonTransformer;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import router.routes.Routes;

import static spark.Spark.get;
import static spark.Spark.port;

public class Router {
    private final static Logger logger = LoggerFactory.getLogger(Router.class);
    public static void start() {
        port(Env.getPort());
        logger.info("Starting server");
        Routes.getAll().forEach(route -> {
            logger.trace("Adding " + route.getName() + " to api.");
            get("/" + route.getName(), ((request, response) -> {
                Object data = route.handleServiceRequest(request, response);
                if (data == null) {
                    response.status(HttpStatus.SC_BAD_REQUEST);
                    return null;
                }
                return data;
            }), new JsonTransformer());
        });
    }
}
