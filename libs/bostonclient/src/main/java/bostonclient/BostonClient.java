package bostonclient;

import bostonclient.apis.PBHealthAPI;
import bostonclient.apis.TaskDefectDensityAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;
    private static TaskDefectDensityAPI tddAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBHealthAPI() {
        return pbHealthAPI;
    }

    public static TaskDefectDensityAPI getTaskDefectDensityAPI() {
        return tddAPI;
    }
}
