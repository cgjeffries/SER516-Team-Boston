package bostonhttp.api;

import bostonhttp.models.AuthToken;

public class APIWrapperBehaviors {
    private BaseApiUrlResolver baseApiUrlResolver;

    private AuthToken authToken;

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

    public APIWrapperBehaviors withAuthToken(AuthToken token){
        this.authToken = token;
        return this;
    }

    public AuthToken getAuthToken(){
        return authToken;
    }
}
