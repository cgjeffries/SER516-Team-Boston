package bostonclient;

import bostonclient.apis.PBChangeAPI;
import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskChurnAPI;
import bostonclient.apis.TaskDefectDensityAPI;
import bostonclient.apis.TaskExcessAPI;
import bostonclient.apis.TaskInertiaAPI;

public class BostonClient {
    private static PBChangeAPI pbChangeAPI;
    private static PBHealthAPI pbHealthAPI;
    private static TaskChurnAPI taskChurnAPI;
    private static TaskDefectDensityAPI tddAPI;
    private static TaskExcessAPI taskExcessAPI;
    private static TaskInertiaAPI taskInertiaAPI;

    public static void buildClient(BostonClientOptions options) {
        pbChangeAPI = new PBChangeAPI(options.getRouterUrl());
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
        taskChurnAPI = new TaskChurnAPI(options.getRouterUrl());
        taskExcessAPI = new TaskExcessAPI(options.getRouterUrl());
        tddAPI = new TaskDefectDensityAPI(options.getRouterUrl());
        taskInertiaAPI = new TaskInertiaAPI(options.getRouterUrl());
    }

    public static PBChangeAPI getPBChangeAPI() {
        return pbChangeAPI;
    }
    public static PBHealthAPI getPBHealthAPI() {
        return pbHealthAPI;
    }
    public static TaskChurnAPI getTaskChurnAPI(){
        return taskChurnAPI;
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
