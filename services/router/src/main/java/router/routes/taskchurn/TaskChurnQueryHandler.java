package router.routes.taskchurn;

import bostonmodel.taskchurn.TaskChurnMetrics;
import org.apache.http.HttpStatus;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicReference;

public class TaskChurnQueryHandler extends RouteQueryHandler<Object> {
    private final TaskChurnAPI api;

    public TaskChurnQueryHandler(TaskChurnAPI api) {
        this.api = api;
    }


    @Override
    public boolean matches(Request request) {
        return request.queryParams().contains("sprint_id");
    }

    @Override
    public Object handle(Request request, Response response) {
        AtomicReference<TaskChurnMetrics> apiResult = new AtomicReference<>(null);
        api.getTaskChurn(Integer.parseInt(request.queryParams("sprint_id")),
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
