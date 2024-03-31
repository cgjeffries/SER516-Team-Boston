package routes.burndown;

import bostonhttp.api.APIResponse;
import routes.RouteAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class BurndownAPI extends RouteAPI {

    public BurndownAPI(String serviceName, String serviceUrl) {
        super(serviceName, serviceUrl);
    }

    public CompletableFuture<Void> getBurndown(String sprint, String startDate, String endDate, Consumer<APIResponse<Object>> callback) {
        return queryAsync("?sprint=" + sprint + "&start_date=" + startDate + "&end_date=" + endDate, Object.class).thenAccept(callback);
    }
}
