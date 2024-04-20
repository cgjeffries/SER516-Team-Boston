package router.routes.taskexcess;

import bostonmodel.taskexcess.TaskExcessMetrics;
import org.apache.http.HttpStatus;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicReference;

public class TaskExcessQueryHandler extends RouteQueryHandler<Object> {
    private final TaskExcessAPI api;

    public TaskExcessQueryHandler(TaskExcessAPI api) {
        this.api = api;
    }


    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("sprint_id");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<TaskExcessMetrics> apiResult = new AtomicReference<>(null);
        api.getTaskExcess(Integer.parseInt(request.queryParams("sprint_id")),
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
