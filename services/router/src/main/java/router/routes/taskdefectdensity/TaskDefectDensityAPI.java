package router.routes.taskdefectdensity;

import bostonhttp.api.APIResponse;
import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskDefectDensityAPI extends RouteAPI {
    public TaskDefectDensityAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getTaskDefectDensity(
            int sprintId,
            Consumer<APIResponse<TaskDefectDensityMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskDefectDensityMetrics.class)
                .thenAccept(callback);
    }
}
