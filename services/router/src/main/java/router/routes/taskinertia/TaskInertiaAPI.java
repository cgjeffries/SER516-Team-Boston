package router.routes.taskinertia;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;
import bostonmodel.taskinertia.TaskInertiaMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

public class TaskInertiaAPI extends RouteAPI {

    public TaskInertiaAPI(Route route) {
        super(route);
    }
    
    public CompletableFuture<Void> getTaskInertia(int projectId, String startDate, String endDate, Consumer<APIResponse<TaskInertiaMetrics>> callback) {
        return queryAsync("?project_id=" + projectId + "&start_date=" + startDate + "&end_date=" + endDate , TaskInertiaMetrics.class).thenAccept(callback);
    }
}
