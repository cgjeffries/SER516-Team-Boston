package router.routes.pbchange;

import java.util.List;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

public class PBChangeRoute extends Route {
    private final PBChangeAPI api;

    public PBChangeRoute() {
        this.api = new PBChangeAPI(this);
    }

    @Override
    public String getName() {
        return "pbchange";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://boston-pbchange:9004/";
    }

    @Override
    public String getProductionHost() {
        return "http://boston-pbchange.railway.internal:9004/";
    }

    @Override
    public Object getDefaultHandler(Request request, Response response) {
        return "";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new PBChangeQueryHandler(api));
    }
}
