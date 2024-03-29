package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.taskhistory.ItemHistory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserStoryHistoryAPI extends APIWrapperBase {
    public UserStoryHistoryAPI() {
        super("history/userstory");
    }

    /**
     * Get the history for the User story with the given ID asynchronously.
     *
     * @param id       ID of the task.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryHistory(
            int id, Consumer<APIResponse<ItemHistory[]>> callback) {
        return queryAsync("/" + id, ItemHistory[].class).thenAccept(callback);
    }
}
