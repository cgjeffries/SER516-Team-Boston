package bostonclient.apis;

import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;
import bostonmodel.taskexcess.TaskExcessMetrics;

public class TaskDefectDensityAPI extends MetricAPI {

    public TaskDefectDensityAPI(String routerUrl) {
        super("taskdefectdensity", routerUrl);
    }

    public CompletableFuture<Void> getTaskDD(int sprintId, Consumer<APIResponse<TaskDefectDensityMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, TaskDefectDensityMetrics.class).thenAccept(callback);
    }
}
