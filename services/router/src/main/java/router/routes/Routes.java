package router.routes;

import java.util.List;

import router.routes.pbchange.PBChangeRoute;
import router.routes.pbhealth.PBHealthRoute;
import router.routes.scopechange.ScopeChangeRoute;
import router.routes.taskdefectdensity.TaskDefectDensityRoute;
import router.routes.taskexcess.TaskExcessRoute;
import router.routes.taskinertia.TaskInertiaRoute;

public class Routes {
    public static List<Route> getAll() {
        return List.of(
                new PBHealthRoute(),
                new ScopeChangeRoute(),
                new TaskExcessRoute(),
                new TaskInertiaRoute(),
                new PBChangeRoute(),
                new TaskDefectDensityRoute()
        );
    }
}
