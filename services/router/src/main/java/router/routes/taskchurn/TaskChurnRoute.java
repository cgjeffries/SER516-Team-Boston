package router.routes.taskchurn;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.List;

public class TaskChurnRoute extends Route {
    private final TaskChurnAPI api;

    public TaskChurnRoute() {
        this.api = new TaskChurnAPI(this);
    }

    @Override
    public String getName() {
        return "taskchurn";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-taskchurn:9006/";
    }

    @Override
    public String getProductionHost() {
        return "http://boston-taskchurn.railway.internal:9006/";
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new TaskChurnQueryHandler(api)
        );
    }
}