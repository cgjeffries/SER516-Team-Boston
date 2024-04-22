package bostonclient;

import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskChurnAPI;
import bostonclient.apis.TaskExcessAPI;
import bostonclient.apis.TaskInertiaAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;
    private static TaskChurnAPI taskChurnAPI;
    private static TaskExcessAPI taskExcessAPI;
    private static TaskInertiaAPI taskInertiaAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
        taskChurnAPI = new TaskChurnAPI(options.getRouterUrl());
        taskExcessAPI = new TaskExcessAPI(options.getRouterUrl());
        taskInertiaAPI = new TaskInertiaAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBChangeAPI() {
        return pbHealthAPI;
    }
    public static TaskChurnAPI getTaskChurnAPI(){
        return taskChurnAPI;
    }

    public static TaskExcessAPI getTaskExcessAPI(){
        return taskExcessAPI;
    }

    public static TaskInertiaAPI getTaskInertiaAPI() {
        return taskInertiaAPI;
    }
}
