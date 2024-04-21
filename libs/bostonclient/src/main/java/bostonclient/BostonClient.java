package bostonclient;

import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskExcessAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;
    private static TaskExcessAPI taskExcessAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
        taskExcessAPI = new TaskExcessAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBChangeAPI() {
        return pbHealthAPI;
    }

    public static TaskExcessAPI getTaskExcessAPI(){
        return taskExcessAPI;
    }
}
