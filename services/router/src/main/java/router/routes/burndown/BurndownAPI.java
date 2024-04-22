package router.routes.burndown;

import bostonhttp.api.APIResponse;
import bostonmodel.burndown.BurndownMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BurndownAPI extends RouteAPI {
    /**
     * Create an API wrapper for a given microservice. Given a service name and the service url, this
     * class will direct API requests to {serviceUrl}/{serviceName}
     *
     * @param route the route for this api
     */
    public BurndownAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getBurndown(
            int sprintId,
            Consumer<APIResponse<BurndownMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, BurndownMetrics.class)
                .thenAccept(callback);
    }
}
