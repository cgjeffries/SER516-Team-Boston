package router.routes.taskdefectdensity;

import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import org.apache.http.HttpStatus;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicReference;

public class TaskDefectDensityQueryHandler extends RouteQueryHandler<Object> {
    private final TaskDefectDensityAPI api;

    public TaskDefectDensityQueryHandler(TaskDefectDensityAPI api) {
        this.api = api;
    }


    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("sprint_id");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<TaskDefectDensityMetrics> apiResult = new AtomicReference<>(null);
        api.getTaskDefectDensity(Integer.parseInt(request.queryParams("sprint_id")),
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

