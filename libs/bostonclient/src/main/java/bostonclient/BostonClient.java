package bostonclient;

import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskDefectDensityAPI;
import bostonclient.apis.TaskExcessAPI;
import bostonclient.apis.TaskInertiaAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;
    private static TaskDefectDensityAPI tddAPI;
    private static TaskExcessAPI taskExcessAPI;
    private static TaskInertiaAPI taskInertiaAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
        taskExcessAPI = new TaskExcessAPI(options.getRouterUrl());
        tddAPI = new TaskDefectDensityAPI(options.getRouterUrl());
        taskInertiaAPI = new TaskInertiaAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBHealthAPI() {
        return pbHealthAPI;
    }

    public static TaskExcessAPI getTaskExcessAPI(){
        return taskExcessAPI;
    }

    public static TaskDefectDensityAPI getTaskDefectDensityAPI() {
        return tddAPI;
    }

    public static TaskInertiaAPI getTaskInertiaAPI() {
        return taskInertiaAPI;
    }
}
