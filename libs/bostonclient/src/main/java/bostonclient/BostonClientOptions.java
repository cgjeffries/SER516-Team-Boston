package bostonclient;

public class BostonClientOptions {
    private String routerUrl;

    public void withRouter(String routerUrl) {
        this.routerUrl = routerUrl;
    }

    public String getRouterUrl() {
        return routerUrl;
    }
}
