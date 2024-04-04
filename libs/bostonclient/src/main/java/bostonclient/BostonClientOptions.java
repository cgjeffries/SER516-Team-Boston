package bostonclient;

public class BostonClientOptions {
    private String routerUrl;

    public BostonClientOptions withRouter(String routerUrl) {
        this.routerUrl = routerUrl;
        return this;
    }

    public String getRouterUrl() {
        return routerUrl;
    }
}
