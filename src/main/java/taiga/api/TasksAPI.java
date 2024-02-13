package taiga.api;

import taiga.model.query.tasks.Task;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TasksAPI extends APIWrapperBase {
    public TasksAPI() {
        super("tasks");
    }

    /**
     * Get a list of closed {@link Task}s from a project asynchronously.
     *
     * @param project The project id to get tasks from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listClosedTasksByProject(
            int project, Consumer<APIResponse<Task[]>> callback) {
        return this.listTasksByProject(project, true, callback);
    }

    /**
     * Get a list of {@link Task}s from a project asynchronously.
     *
     * @param project The project id to get tasks from
     * @param closed true to get closed tasks, false to get open tasks
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listTasksByProject(
            int project, boolean closed, Consumer<APIResponse<Task[]>> callback) {
        return queryAsync("?project=" + project + "&status__is_closed=" + closed, Task[].class)
                .thenAccept(callback);
    }

    /**
     * Get a list of closed {@link Task}s from a project asynchronously.
     *
     * @param milestone The milestone id to get tasks from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listClosedTasksByMilestone(
            int milestone, Consumer<APIResponse<Task[]>> callback) {
        return this.listTasksByMilestone(milestone, true, callback);
    }

    /**
     * Get a list of {@link Task}s from a project asynchronously.
     *
     * @param milestone The milestone id to get tasks from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listTasksByMilestone(
            int milestone, Consumer<APIResponse<Task[]>> callback) {
        return queryAsync("?milestone=" + milestone, Task[].class).thenAccept(callback);
    }

    /**
     * Get a list of {@link Task}s from a project asynchronously.
     *
     * @param milestone The milestone id to get tasks from
     * @param closed true to get closed tasks, false to get open tasks
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listTasksByMilestone(
            int milestone, boolean closed, Consumer<APIResponse<Task[]>> callback) {
        return queryAsync("?milestone=" + milestone + "&status__is_closed=" + closed, Task[].class)
                .thenAccept(callback);
    }

    /**
     * Get a list of closed {@link Task}s from a project asynchronously.
     *
     * @param userStory The user story id to get tasks from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listClosedTasksByUserStory(
            int userStory, Consumer<APIResponse<Task[]>> callback) {
        return this.listTasksByUserStory(userStory, true, callback);
    }

    /**
     * Get a list of {@link Task}s from a project asynchronously.
     *
     * @param userStory The user story id to get tasks from
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listTasksByUserStory(
            int userStory, Consumer<APIResponse<Task[]>> callback) {
        return queryAsync("?user_story=" + userStory, Task[].class).thenAccept(callback);
    }

    /**
     * Get a list of {@link Task}s from a project asynchronously.
     *
     * @param userStory The user story id to get tasks from
     * @param closed true to get closed tasks, false to get open tasks
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listTasksByUserStory(
            int userStory, boolean closed, Consumer<APIResponse<Task[]>> callback) {
        return queryAsync("?user_story=" + userStory + "&status__is_closed=" + closed, Task[].class)
                .thenAccept(callback);
    }
}
