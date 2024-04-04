package bostonclient;

import bostonclient.apis.GroomRateAPI;

public class BostonClient {
    private final GroomRateAPI groomRateAPI;

    public BostonClient(BostonClientOptions options) {
        this.groomRateAPI = new GroomRateAPI(options.getOrchestratorUrl());
        // TODO: add the rest of the service apis
    }

    public GroomRateAPI getGroomRateAPI() {
        return groomRateAPI;
    }
}
