package router.routes.pbchange;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonhttp.api.APIResponse;
import bostonmodel.pbchange.PBChangeMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

public class PBChangeAPI extends RouteAPI {

    public PBChangeAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getPBChange(
            int sprintId,
            Consumer<APIResponse<PBChangeMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, PBChangeMetrics.class)
                .thenAccept(callback);
    }
}
