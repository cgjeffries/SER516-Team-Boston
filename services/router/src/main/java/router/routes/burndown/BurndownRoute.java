package router.routes.burndown;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.List;

public class BurndownRoute extends Route {
    private final BurndownAPI api;

    public BurndownRoute() {
        this.api = new BurndownAPI(this);
    }

    @Override
    public String getName() {
        return "burndown";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-burndown:9007/";
    }

    @Override
    public String getProductionHost() {
        return "http://boston-burndown.railway.internal:9007/";
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(new BurndownChangeQueryHandler(api));
    }
}
