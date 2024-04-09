package router.routes.pbhealth;

import bostonhttp.api.APIResponse;
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
            double lowThreshold,
            double midThreshold,
            double highThreshold,
            Consumer<APIResponse<Object>> callback) {
        return queryAsync("?project_id=" + projectId + "&low_threshold=" + lowThreshold + "&mid_threshold="
                + midThreshold + "&high_threshold=" + highThreshold, Object.class)
                .thenAccept(callback);
    }
}
