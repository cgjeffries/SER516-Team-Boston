package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.sprint.SprintStats;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SprintStatsAPI extends APIWrapperBase {
    public SprintStatsAPI() {
        super("milestones");
    }

    /**
     * Get a {@link SprintStats} object from a project asynchronously.
     *
     * @param sprintId The sprintId to get Sprint Stats from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getSprintStats(
            int sprintId, Consumer<APIResponse<SprintStats>> callback) {
        return queryAsync("/" + sprintId + "/stats", SprintStats.class).thenAccept(callback);
    }
}
