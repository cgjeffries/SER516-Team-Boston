package bostonclient.apis;

import bostonhttp.api.APIWrapperBase;
import bostonhttp.api.APIWrapperBehaviors;

class MetricAPI extends APIWrapperBase {

    public MetricAPI(String serviceName, String orchestratorUrl) {
        super(serviceName, new APIWrapperBehaviors().withBaseApiUrlResolver(() -> orchestratorUrl));
    }
}
