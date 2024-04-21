package bostonclient.apis;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;
import bostonmodel.taskexcess.TaskExcessMetrics;

public class TaskExcessAPI extends MetricAPI{
    public TaskExcessAPI(String routerUrl) {
        super("taskexcess", routerUrl);
    }

    public CompletableFuture<Void> getTaskExcess(int sprintId, Consumer<APIResponse<TaskExcessMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskExcessMetrics.class).thenAccept(callback);
    }
}
