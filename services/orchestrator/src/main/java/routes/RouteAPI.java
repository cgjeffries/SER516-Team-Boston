package routes;

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
     * @param serviceName The service name
     * @param serviceUrl  The url of the service
     */
    public RouteAPI(String serviceName, String serviceUrl) {
        super(serviceName, new APIWrapperBehaviors().withBaseApiUrlResolver(() -> serviceUrl));
    }
}
