package orchestrator.routes.groomrate;

import orchestrator.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicReference;

class GroomRateQueryHandler extends RouteQueryHandler<Object> {
    private final GroomRateAPI api;

    public GroomRateQueryHandler(GroomRateAPI api) {
        this.api = api;
    }

    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("start_date") && request.queryParams().contains("end_date");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<Object> apiResult = new AtomicReference<>();
        api.getGroomRate(request.queryParams("start_date"), request.queryParams("end_date"), result -> {
            if (result == null) {
                response.status(500);
            } else {
                response.status(result.getStatus());
                apiResult.set(result.getContent());
            }
        }).join();
        return apiResult.get();
    }
}
