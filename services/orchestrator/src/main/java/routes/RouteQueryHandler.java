package routes;

import spark.Request;
import spark.Response;

/**
 * <p>
 * A class for defining a query handler. The goal of a query handler is
 * to provide a way to handle multiple requests to the same URL path and is used
 * in conjunction
 * with {@link Route}.
 * </p>
 *
 * <p>
 * Normally, this should be used for query parameter filtering. For example, if
 * you needed to define the following routes:
 * 
 * <pre>
 * https://server.com/route1
 *https://server.com/route1?foo=bar
 * </pre>
 * 
 * 
 * you would define two query handlers for route1 - one whose `matches` call
 * would directly return true,
 * and another that checks if "foo" exists on the request's query parameters.
 * </p>
 *
 * @param <T> the return type for this handler
 */
public abstract class RouteQueryHandler<T> {
    /**
     * Condition for this handler to run. Typically used to check for the
     * presence of query parameters.
     * 
     * @param request the spark request
     * @return boolean indicating whether the handler should be run
     */
    public abstract boolean matches(Request request);

    /**
     * Handle a request.
     * 
     * @param request  the spark request object
     * @param response the spark response object
     * @return data to be returned on the parent route
     */
    public abstract T handle(Request request, Response response);
}
