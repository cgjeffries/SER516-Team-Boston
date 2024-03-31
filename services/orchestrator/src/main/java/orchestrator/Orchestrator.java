package orchestrator;

import org.apache.http.HttpStatus;
import routes.Routes;

import static spark.Spark.get;
import static spark.Spark.port;

public class Orchestrator {
    public static void start() {
        port(8000);
        System.out.println("Starting server");
        Routes.getAllRoutes().forEach(route -> {
            System.out.println("Adding " + route.getName() + " to api.");
            get("/" + route.getName(), ((request, response) -> {
                Object data = route.handleServiceRequest(request, response);
                if (data == null) {
                    response.status(HttpStatus.SC_BAD_REQUEST);
                    return "{}";
                }
                return data;
            }));
        });
        get("/burndown_service", ((request, response) -> "hello"));
    }
}
