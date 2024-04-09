package bostonclient.apis;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonclient.models.PBHealthResponse;
import bostonhttp.api.APIResponse;

public class PBHealthAPI extends MetricAPI {

    public PBHealthAPI(String routerUrl) {
        super("pbhealth", routerUrl);
    }

    public CompletableFuture<Void> getPBHealth(int projectId, double lowThreshold, double midThreshold, double highThreshold,
            Consumer<APIResponse<PBHealthResponse>> callback) {
        return queryAsync("?project_id=" + projectId + "&low_threshold=" + lowThreshold + "&mid_threshold=" + midThreshold + "&high_threshold=" + highThreshold, PBHealthResponse.class)
                .thenAccept(callback);
    }
}
