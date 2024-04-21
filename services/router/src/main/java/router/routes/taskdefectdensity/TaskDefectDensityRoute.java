package router.routes.taskdefectdensity;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.List;

public class TaskDefectDensityRoute extends Route {
    private final TaskDefectDensityAPI api;

    public TaskDefectDensityRoute() {
        this.api = new TaskDefectDensityAPI(this);
    }

    @Override
    public String getName() {
        return "taskdefectdensity";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-taskdefectdensity:9005/";
    }

    @Override
    public String getProductionHost() {

        return "http://boston-taskdefectdensity.railway.internal:9005/";
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new TaskDefectDensityQueryHandler(api)
        );
    }
}
