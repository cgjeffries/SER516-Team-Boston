package bostonhttp.api;

public class APIWrapperBehaviors {
    private BaseApiUrlResolver baseApiUrlResolver;

    public APIWrapperBehaviors withBaseApiUrlResolver(BaseApiUrlResolver resolver) {
        this.baseApiUrlResolver = resolver;
        return this;
    }

    public BaseApiUrlResolver getBaseApiUrlResolver() {
        return baseApiUrlResolver;
    }

    public interface BaseApiUrlResolver {
        String resolve();
    }
}
