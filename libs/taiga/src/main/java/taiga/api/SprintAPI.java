package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.sprint.Sprint;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SprintAPI extends APIWrapperBase {
    public SprintAPI() {
        super("milestones");
    }

    /**
     * Get a list of {@link Sprint} objects from a project asynchronously.
     *
     * @param project  The project id to get milestones from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listSprints(
            int project, Consumer<APIResponse<Sprint[]>> callback) {
        return queryAsync("?project=" + project, Sprint[].class).thenAccept(callback);
    }

    /**
     * Get a list of {@link Sprint} objects from a project asynchronously.
     *
     * @param project  The project id to get milestones from
     * @param closed   Whether to return closed or opened milestones
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listSprints(
            int project, boolean closed, Consumer<APIResponse<Sprint[]>> callback) {
        return queryAsync("?project=" + project + "&closed=" + closed, Sprint[].class)
                .thenAccept(callback);
    }

    /**
     * Get all open {@link Sprint}s from a project asynchronously.
     *
     * @param project  The project id to get milestones from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listOpenSprints(
            int project, Consumer<APIResponse<Sprint[]>> callback) {
        return this.listSprints(project, false, callback);
    }

    /**
     * Get all closed {@link Sprint}s from a project asynchronously.
     *
     * @param project  The project id to get milestones from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listClosedSprints(
            int project, Consumer<APIResponse<Sprint[]>> callback) {
        return this.listSprints(project, true, callback);
    }

    public CompletableFuture<Void> getSprint(int sprintId, Consumer<APIResponse<Sprint>> callback) {
        return queryAsync("/" + sprintId, Sprint.class).thenAccept(callback);
    }
}
