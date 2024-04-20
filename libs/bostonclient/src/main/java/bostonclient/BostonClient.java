package bostonclient;

import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskInertiaAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;
    private static TaskInertiaAPI taskInertiaAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
        taskInertiaAPI = new TaskInertiaAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBChangeAPI() {
        return pbHealthAPI;
    }

    public static TaskInertiaAPI getTaskInertiaAPI() {
        return taskInertiaAPI;
    }
}
