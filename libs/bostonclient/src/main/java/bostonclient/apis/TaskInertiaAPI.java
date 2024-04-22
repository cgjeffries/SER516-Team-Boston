package bostonclient.apis;

import bostonhttp.api.APIResponse;
import bostonmodel.taskinertia.TaskInertiaMetrics;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskInertiaAPI extends MetricAPI{
    public TaskInertiaAPI(String routerUrl) {
        super("taskinertia", routerUrl);
    }

    public CompletableFuture<Void> getTaskInertia(int projectId, String startDate, String endDate, Consumer<APIResponse<TaskInertiaMetrics>> callback) {
        return queryAsync("?project_id=" + projectId + "&start_date=" + startDate + "&end_date=" + endDate , TaskInertiaMetrics.class).thenAccept(callback);
    }
}
