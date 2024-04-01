package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.user.UserProfile;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UsersAPI extends APIWrapperBase {
    public UsersAPI() {
        super("users");
    }

    /**
     * Get the logged-in user asynchronously.
     *
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getMe(Consumer<APIResponse<UserProfile>> callback) {
        return queryAsync("/me", UserProfile.class).thenAccept(callback);
    }
}