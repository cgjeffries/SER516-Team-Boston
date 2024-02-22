package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.user.UserProfile;

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