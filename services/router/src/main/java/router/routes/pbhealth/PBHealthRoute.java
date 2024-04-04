package router.routes.pbhealth;

import java.util.List;

import router.routes.Route;
import router.routes.RouteQueryHandler;
import spark.Request;
import spark.Response;

public class PBHealthRoute extends Route {
    private final PBHealthAPI api;

    public PBHealthRoute() {
        this.api = new PBHealthAPI(this);
    }

    @Override
    public String getName() {
        return "pbhealth";
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
    public Object getDefaultHandler(Request request, Response response) {
        return "america ya! :D";
    }

    @Override
    public List<RouteQueryHandler<Object>> getRouteQueryHandlers() {
        return List.of(
                new PBHealthQueryHandler(api)
        );
    }
}
