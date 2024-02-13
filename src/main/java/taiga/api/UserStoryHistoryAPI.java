package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.taskhistory.TaskHistory;

public class UserStoryHistoryAPI extends APIWrapperBase {
    public UserStoryHistoryAPI() {
        super("history/userstory");
    }

    /**
     * Get the task history for the task with the given ID asynchronously.
     *
     * @param id ID of the task.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryHistory(
            int id, Consumer<APIResponse<TaskHistory[]>> callback) { //TODO: determine if we actually need a separate UserStoryHistory class?
        return queryAsync("/" + id, TaskHistory[].class).thenAccept(callback);
    }
}
