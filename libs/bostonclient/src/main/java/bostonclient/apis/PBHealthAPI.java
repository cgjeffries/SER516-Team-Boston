package bostonclient.apis;

import bostonmodel.pbhealth.PBHealthMetrics;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;

public class PBHealthAPI extends MetricAPI {

    public PBHealthAPI(String routerUrl) {
        super("pbhealth", routerUrl);
    }

    public CompletableFuture<Void> getPBHealth(int projectId,
            Consumer<APIResponse<PBHealthMetrics>> callback) {
        return queryAsync("?project_id=" + projectId, PBHealthMetrics.class)
                .thenAccept(callback);
    }
}
