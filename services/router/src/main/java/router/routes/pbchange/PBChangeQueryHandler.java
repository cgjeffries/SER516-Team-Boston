package router.routes.pbchange;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpStatus;

import bostonmodel.pbchange.PBChangeMetrics;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

public class PBChangeQueryHandler extends RouteQueryHandler<Object> {
    private final PBChangeAPI api;

    public PBChangeQueryHandler(PBChangeAPI api) {
        this.api = api;
    }

    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("sprint_id");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<PBChangeMetrics> apiResult = new AtomicReference<>(null);
        api.getPBChange(Integer.parseInt(request.queryParams("sprint_id")),
                result -> {
                    if (result == null) {
                        response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                    } else {
                        response.status(result.getStatus());
                        apiResult.set(result.getContent());
                    }
                }).join();
        return apiResult.get();
    }
}
