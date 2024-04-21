package router.routes.taskchurn;

import bostonhttp.api.APIResponse;
import bostonmodel.taskchurn.TaskChurnMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskChurnAPI extends RouteAPI {
    public TaskChurnAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getTaskChurn(
            int sprintId,
            Consumer<APIResponse<TaskChurnMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskChurnMetrics.class)
                .thenAccept(callback);
    }
}