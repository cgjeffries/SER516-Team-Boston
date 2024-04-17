package router.routes.taskexcess;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.List;

public class TaskExcessRoute extends Route {
    private final TaskExcessAPI api;

    public TaskExcessRoute() {
        this.api = new TaskExcessAPI(this);
    }

    @Override
    public String getName() {
        return "taskexcess";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-taskexcess:9003/";
    }

    @Override
    public String getProductionHost() {
        // TODO: place an actual production host here
        return null;
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new TaskExcessQueryHandler(api)
        );
    }
}
