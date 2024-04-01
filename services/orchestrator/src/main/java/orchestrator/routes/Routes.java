package orchestrator.routes;

import java.util.List;

import orchestrator.routes.groomrate.GroomRateRoute;

public class Routes {
    public static List<Route> getAll() {
        return List.of(
                new GroomRateRoute()
        );
    }
}
