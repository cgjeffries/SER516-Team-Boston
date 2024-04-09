package router.routes.pbhealth;

import bostonhttp.api.APIResponse;
import bostonmodel.pbhealth.PBHealthMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class PBHealthAPI extends RouteAPI {

    public PBHealthAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getPBHealth(
            int projectId,
            Consumer<APIResponse<PBHealthMetrics>> callback) {
        return queryAsync("?project_id=" + projectId, PBHealthMetrics.class)
                .thenAccept(callback);
    }
}
