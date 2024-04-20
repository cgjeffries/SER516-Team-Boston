package bostonclient.apis;

import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;

public class TaskDefectDensityAPI extends MetricAPI {

    public TaskDefectDensityAPI(String routerUrl) {
        super("taskdefectdensity", routerUrl);
    }

    public CompletableFuture<Void> getTaskDD(int projectId, Consumer<APIResponse<TaskDefectDensityMetrics>> callback) {
        return queryAsync("?project_id=" + projectId, TaskDefectDensityMetrics.class)
        .thenAccept(callback);
}
}
