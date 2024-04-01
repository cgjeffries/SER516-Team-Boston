package orchestrator.routes;

import bostonhttp.api.APIWrapperBase;
import bostonhttp.api.APIWrapperBehaviors;

/**
 * Base class for defining API wrappers {@link Route}s that want to communicate with a microservice.
 */
public class RouteAPI extends APIWrapperBase {
    /**
     * Create an API wrapper for a given microservice. Given a service name and the service url, this
     * class will direct API requests to {serviceUrl}/{serviceName}
     *
     * @param route the route for this api
     */
    public RouteAPI(Route route) {
        super(route.getName(), new APIWrapperBehaviors().withBaseApiUrlResolver(route::getHost));
    }
}
