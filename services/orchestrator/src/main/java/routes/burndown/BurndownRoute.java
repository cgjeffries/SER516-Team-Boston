package routes.burndown;

import routes.QueryHandler;
import routes.Route;

import java.util.List;

public class BurndownRoute extends Route {
    private final BurndownAPI api;

    public BurndownRoute() {
        this.api = new BurndownAPI(getName(), getDevelopmentHost());
    }

    @Override
    public String getName() {
        return "burndown";
    }

    @Override
    public String getDevelopmentHost() {
        return "http://localhost:9000/";
    }

    @Override
    public String getProductionHost() {
        return null;
    }

    @Override
    public Object getDefaultHandler() {
        return "hello :D";
    }

    @Override
    public List<QueryHandler<Object>> getQueryHandlers() {
        return List.of(
                new BurndownQueryHandler(api)
        );
    }
}
