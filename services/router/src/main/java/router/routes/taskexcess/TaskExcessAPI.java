package router.routes.taskexcess;

import bostonhttp.api.APIResponse;
import bostonmodel.taskexcess.TaskExcessMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskExcessAPI extends RouteAPI {
    /**
     * Create an API wrapper for a given microservice. Given a service name and the service url, this
     * class will direct API requests to {serviceUrl}/{serviceName}
     *
     * @param route the route for this api
     */
    public TaskExcessAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getTaskExcess(int sprintId, Consumer<APIResponse<TaskExcessMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskExcessMetrics.class).thenAccept(callback);
    }
}
