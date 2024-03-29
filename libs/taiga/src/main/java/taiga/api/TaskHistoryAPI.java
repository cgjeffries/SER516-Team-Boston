package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.taskhistory.ItemHistory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TaskHistoryAPI extends APIWrapperBase {
    public TaskHistoryAPI() {
        super("history/task");
    }


    /**
     * Get the history for the task with the given ID asynchronously.
     *
     * @param id       ID of the task.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getTaskHistory(
            int id, Consumer<APIResponse<ItemHistory[]>> callback) {
        return queryAsync("/" + id, ItemHistory[].class).thenAccept(callback);
    }
}
