package router.routes;

import spark.Request;
import spark.Response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import router.Env;

/**
 * Base class for defining a route for a microservice.
 */
public abstract class Route {
    private static final Logger logger = LoggerFactory.getLogger(Route.class);
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

    /**
     * A default handler to use when no suitable {@link RouteQueryHandler}s are found
     * 
     * @return the handler response data
     */
    public abstract Object getDefaultHandler(Request request, Response response);

    /**
     * Get a list of query handlers associated with this route. See
     * {@link RouteQueryHandler} for more details.
     *
     * @return A list of query handlers.
     */
    public abstract List<RouteQueryHandler<Object>> getRouteQueryHandlers();


    public String getHost() {
        return Env.isDevEnv() ? getDevelopmentHost() : getProductionHost();
    }
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
        List<RouteQueryHandler<Object>> matchingHandlers = getRouteQueryHandlers().stream()
                .filter(queryHandler -> queryHandler.matches(request)).toList();
        if (matchingHandlers.isEmpty()) {
            logger.info("No matching query handlers found, running default handler.");
            return getDefaultHandler(request, response);
        }
        if (matchingHandlers.size() != 1) {
            logger.warn("Warning: " + matchingHandlers.size() + " handlers found for request to " + request.url()
                    + ", selecting first handler.");
        }
        RouteQueryHandler<Object> handler = matchingHandlers.get(0);
        return handler.handle(request, response);
    }
}
