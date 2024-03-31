package routes;

import routes.burndown.BurndownRoute;

import java.util.List;

public class Routes {
    public static List<Route> getAllRoutes() {
        return List.of(
                new BurndownRoute()
        );
    }
}
