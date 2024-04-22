package bostonclient.apis;

import bostonhttp.api.APIResponse;
import bostonmodel.pbchange.PBChangeMetrics;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PBChangeAPI extends MetricAPI{
    public PBChangeAPI(String routerUrl){
        super("pbchange", routerUrl);
    }

    public CompletableFuture<Void> getPBChange(int sprintId, Consumer<APIResponse<PBChangeMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, PBChangeMetrics.class).thenAccept(callback);
    }
}
