package router.routes.scopechange;

import bostonhttp.api.APIResponse;
import bostonmodel.scopechange.ScopeChangeMetrics;
import router.routes.Route;
import router.routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ScopeChangeAPI extends RouteAPI {

    public ScopeChangeAPI(Route route) {
        super(route);
    }

    public CompletableFuture<Void> getScopeChange(
            int sprintId,
            Consumer<APIResponse<ScopeChangeMetrics>> callback) {
        return queryAsync("?sprint_id=" + sprintId, ScopeChangeMetrics.class)
                .thenAccept(callback);
    }
}
