package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.auth.LoginResponse;
import taiga.model.auth.RefreshResponse;
import taiga.model.auth.RefreshToken;
import taiga.model.auth.UserPasswordLogin;

public class AuthAPI extends APIWrapperBase {
    public AuthAPI() {
        super("auth");
    }

    /**
     * Authenticate with Taiga using a username and password asynchronously. This will retrieve the
     * auth and refresh tokens.
     *
     * @param username username
     * @param password password
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> authenticate(
            String username, String password, Consumer<APIResponse<LoginResponse>> callback) {
        // Create request body containing username and password
        UserPasswordLogin login = new UserPasswordLogin();
        login.setPassword(password);
        login.setUsername(username);
        login.setType("normal");

        return postAsync("", login, LoginResponse.class).thenAccept(callback);
    }

    /**
     * Authenticate with Taiga using a refresh token asynchronously. This will retrieve the auth and
     * refresh tokens.
     *
     * @param refresh refresh token.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> refresh(
            String refresh, Consumer<APIResponse<RefreshResponse>> callback) {
        RefreshToken refreshToken = new RefreshToken(refresh);

        return postAsync("/refresh", refreshToken, RefreshResponse.class, false)
                .thenAccept(callback);
    }
}
