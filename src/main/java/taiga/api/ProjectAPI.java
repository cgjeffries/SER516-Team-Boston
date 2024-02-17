package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import taiga.model.query.project.Project;
import taiga.model.query.project.ProjectListEntry;

public class ProjectAPI extends APIWrapperBase {
    private static final Logger logger = Logger.getLogger(ProjectAPI.class.getName());

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
        return queryAsync("", ProjectListEntry[].class, true, true)
                .thenAccept(callback)
                .exceptionally(throwable -> {
                    handleError(throwable);
                    return null;
                });
    }

    /**
     * Get the project with the given ID asynchronously.
     *
     * @param id id of project.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getProject(int id, Consumer<APIResponse<Project>> callback) {
        return queryAsync("/" + id, Project.class)
                .thenAccept(callback)
                .exceptionally(throwable -> {
                    handleError(throwable);
                    return null;
                });
    }

    /**
     * Get the project with the given slug asynchronously.
     *
     * @param slug slug of project.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getProject(String slug, Consumer<APIResponse<Project>> callback) {
        return queryAsync("/by_slug?slug=" + slug, Project.class)
                .thenAccept(callback)
                .exceptionally(throwable -> {
                    handleError(throwable);
                    return null;
                });
    }

    /**
     * List the projects that the given user is a member of asynchronously.
     *
     * @param id id of user.
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listUserProjects(int id, Consumer<APIResponse<ProjectListEntry[]>> callback) {
        return queryAsync("?member=" + id, ProjectListEntry[].class)
                .thenAccept(callback)
                .exceptionally(throwable -> {
                    handleError(throwable);
                    return null;
                });
    }

    private void handleError(Throwable throwable) {
        logger.log(Level.SEVERE, "An error occurred during API call", throwable);
    }
}