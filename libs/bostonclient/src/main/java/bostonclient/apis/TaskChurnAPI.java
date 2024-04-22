package bostonclient.apis;

import bostonhttp.api.APIResponse;
import bostonmodel.taskchurn.TaskChurnMetrics;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskChurnAPI extends MetricAPI{
    public TaskChurnAPI(String routerUrl){
        super("taskchurn", routerUrl);
    }

    public CompletableFuture<Void> getTaskChurn(int sprintId, Consumer<APIResponse<TaskChurnMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskChurnMetrics.class).thenAccept(callback);
    }
}
