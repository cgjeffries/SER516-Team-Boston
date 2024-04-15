package router.routes.taskinertia;

import java.util.concurrent.atomic.AtomicReference;

import bostonmodel.taskinertia.TaskInertiaMetrics;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

public class TaskInertiaQueryHandler extends RouteQueryHandler<Object> {

    private final TaskInertiaAPI api;

    public TaskInertiaQueryHandler(TaskInertiaAPI api) {
        this.api = api;
    }

    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("project_id")
                && request.queryParams().contains("start_date")
                && request.queryParams().contains("end_date");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<TaskInertiaMetrics> apiResult = new AtomicReference<>(null);
        api.getTaskInertia(
                Integer.parseInt(request.queryParams("project_id")),
                request.queryParams("start_date"),
                request.queryParams("end_date"),
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