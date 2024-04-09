package router.routes.pbhealth;

import bostonmodel.pbhealth.PBHealthMetrics;
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
        return request.queryParams().contains("project_id");
    }

    @Override
    public PBHealthMetrics handle(Request request, Response response) {
        AtomicReference<PBHealthMetrics> apiResult = new AtomicReference<>(null);
        api.getPBHealth(
                Integer.parseInt(request.queryParams("project_id")),
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
