package bostonclient;

public class BostonClientOptions {
    private String orchestratorUrl;

    public void withOrchestratorUrl(String orchestratorUrl) {
        this.orchestratorUrl = orchestratorUrl;
    }

    public String getOrchestratorUrl() {
        return orchestratorUrl;
    }
}
