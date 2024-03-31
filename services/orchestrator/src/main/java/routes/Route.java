package routes;

import spark.Request;
import spark.Response;

import java.util.List;

/**
 * Base class for defining a route for a microservice.
 */
public abstract class Route {
    /**
     * Get the name of the microservice.
     *
     * @return The name of the microservice
     */

    public abstract String getName();

    /**
     * The development host for this route's microservice
     *
     * @return the development host
     */
    public abstract String getDevelopmentHost();

    /**
     * The production host for this route's microservice
     *
     * @return the production host
     */
    public abstract String getProductionHost();

    public abstract Object getDefaultHandler();

    /**
     * Get a list of query handlers associated with this route. See {@link QueryHandler} for more details.
     *
     * @return A list of query handlers.
     */
    public abstract List<QueryHandler<Object>> getQueryHandlers();

    /**
     * Handle an incoming request to this route. This method will look for a
     * query handler that matches on the incoming request. If found, the handler
     * is invoked, and its return value is given back.
     *
     * @param request  The {@link Request} object of the spark route
     * @param response The {@link Response} object of the spark route
     * @return The return value of the query handler
     */
    public Object handleServiceRequest(Request request, Response response) {
        List<QueryHandler<Object>> matchingHandlers = getQueryHandlers().stream().filter(queryHandler -> queryHandler.matches(request)).toList();
        if (matchingHandlers.isEmpty()) {
            return getDefaultHandler();
        }
        if (matchingHandlers.size() != 1) {
            System.err.println("Warning: " + matchingHandlers.size() + " handlers found for request to " + request.url() + ", selecting first handler.");
        }
        QueryHandler<Object> handler = matchingHandlers.get(0);
        return handler.handle(request, response);
    }
}
