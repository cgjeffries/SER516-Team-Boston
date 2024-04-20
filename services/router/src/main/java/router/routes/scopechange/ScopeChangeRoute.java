package router.routes.scopechange;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

import java.util.List;

public class ScopeChangeRoute extends Route{
    private final ScopeChangeAPI api;

    public ScopeChangeRoute() {
        this.api = new ScopeChangeAPI(this);
    }

    @Override
    public String getName() {
        return "scopechange";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-scopechange:9001/";
    }

    @Override
    public String getProductionHost() {
        return "http://boston-scopechange.railway.internal:9001/";
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new ScopeChangeQueryHandler(api)
        );
    }
}
