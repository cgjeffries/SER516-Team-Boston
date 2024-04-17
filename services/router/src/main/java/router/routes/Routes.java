package router.routes;

import java.util.List;

import router.routes.pbhealth.PBHealthRoute;
import router.routes.scopechange.ScopeChangeRoute;

public class Routes {
    public static List<Route> getAll() {
        return List.of(
                new PBHealthRoute(),
                new ScopeChangeRoute()
        );
    }
}
