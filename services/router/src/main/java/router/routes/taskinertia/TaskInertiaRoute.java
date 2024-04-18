package router.routes.taskinertia;

import java.util.List;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

public class TaskInertiaRoute extends Route {

    private final TaskInertiaAPI api;

    public TaskInertiaRoute() {
        this.api = new TaskInertiaAPI(this);
    }

    @Override
    public String getName() {
        return "taskinertia";
    }

    @Override
    public String getDevelopmentHost() {
        // TODO: place an actual development host here
        return "http://boston-taskinertia:9002/";
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
                new TaskInertiaQueryHandler(api));

    }

}