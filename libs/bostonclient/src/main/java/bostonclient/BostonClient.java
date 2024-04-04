package bostonclient;

import bostonclient.apis.PBHealthAPI;

public class BostonClient {
    private PBHealthAPI pbHealthAPI;
    private static BostonClient client = null;

    private BostonClient() {

    }

    public void build(BostonClientOptions options) {
        pbHealthAPI = new PBHealthAPI(options.getRouterUrl());
    }

    public PBHealthAPI getPBChangeAPI() {
        return pbHealthAPI;
    }

    public static BostonClient get() {
        if (client == null) {
            client = new BostonClient();
        }
        return client;
    }
}
