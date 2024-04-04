package router.routes.groomrate;

import router.routes.RouteQueryHandler;
import router.routes.Route;

import java.util.List;

public class GroomRateRoute extends Route {
    private final GroomRateAPI api;

    public GroomRateRoute() {
        this.api = new GroomRateAPI(this);
    }

    @Override
    public String getName() {
        return "groomrate";
    }

    @Override
    public String getDevelopmentHost() {
        // TODO: place an actual development host here
        return "http://localhost:9000/";
    }

    @Override
    public String getProductionHost() {
        // TODO: place an actual production host here
        return null;
    }

    @Override
    public Object getDefaultHandler() {
        return "america ya! :D";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new GroomRateQueryHandler(api)
        );
    }
}
