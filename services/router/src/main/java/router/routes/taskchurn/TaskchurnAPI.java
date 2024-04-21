package router.routes.taskchurn;

import bostonhttp.api.APIResponse;
import bostonmodel.taskchurn.TaskChurnItem;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskchurnAPI extends RouteAPI {
    public TaskchurnAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getTaskChurn(
            int sprintId,
            Consumer<APIResponse<TaskChurnItem>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskChurnItem.class)
                .thenAccept(callback);
    }
}