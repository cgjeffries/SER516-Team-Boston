package router.routes.pbhealth;

import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicReference;

class PBHealthQueryHandler extends RouteQueryHandler<Object> {
    private final PBHealthAPI api;

    public PBHealthQueryHandler(PBHealthAPI api) {
        this.api = api;
    }

    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("project_id")
                && request.queryParams().contains("low_threshold")
                && request.queryParams().contains("mid_threshold")
                && request.queryParams().contains("high_threshold");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<Object> apiResult = new AtomicReference<>(null);
        api.getPBHealth(
                Integer.parseInt(request.queryParams("project_id")),
                Double.parseDouble(request.queryParams("low_threshold")),
                Double.parseDouble(request.queryParams("mid_threshold")),
                Double.parseDouble(request.queryParams("high_threshold")),
                result -> {
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
