package router.routes;

import java.util.List;

import router.routes.pbhealth.PBHealthRoute;
import router.routes.taskinertia.TaskInertiaRoute;

public class Routes {
    public static List<Route> getAll() {
        return List.of(
                new PBHealthRoute(),
                new TaskInertiaRoute()
        );
    }
}
