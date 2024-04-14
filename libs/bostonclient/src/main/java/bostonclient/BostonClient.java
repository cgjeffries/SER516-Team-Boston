package bostonclient;

import bostonclient.apis.PBHealthAPI;

public class BostonClient {
    private static PBHealthAPI pbHealthAPI;

    public static void buildClient(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
    }

    public static PBHealthAPI getPBChangeAPI() {
        return pbHealthAPI;
    }
}
