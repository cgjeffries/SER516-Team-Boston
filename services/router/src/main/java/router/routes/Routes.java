package router.routes;

import java.util.List;

import router.routes.pbhealth.PBHealthRoute;

public class Routes {
    public static List<Route> getAll() {
        return List.of(
                new PBHealthRoute()
        );
    }
}
