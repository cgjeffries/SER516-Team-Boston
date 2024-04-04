package bostonclient.apis;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import bostonclient.models.GroomRateResponse;
import bostonhttp.api.APIResponse;

public class GroomRateAPI extends MetricAPI {

    public GroomRateAPI(String orchestratorUrl) {
        super("groomrate", orchestratorUrl);
    }

    public CompletableFuture<Void> getGroomRate(String startDate, String endDate,
            Consumer<APIResponse<GroomRateResponse>> callback) {
        return queryAsync("?start_date=" + startDate + "&end_date=" + endDate, GroomRateResponse.class)
                .thenAccept(callback);
    }
}
