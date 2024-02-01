package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.project.ProjectListEntry;


public class ProjectAPI extends APIWrapperBase {
    public ProjectAPI() {
        super("projects");
    }

    /**
     * List all projects that you are authorized to view asynchronously.
     *
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listProjects(Consumer<APIResponse<ProjectListEntry[]>> callback) {
        return queryAsync("", ProjectListEntry[].class).thenAccept(callback);
    }

}
