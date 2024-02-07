package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.sprint.UserStoryDetail;

public class UserStoryAPI extends APIWrapperBase {
    public UserStoryAPI() {
        super("userstories");
    }

    /**
     * Lists all user stories user is authorized to view asynchronously.
     *
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listUserStories(
            Consumer<APIResponse<UserStoryDetail[]>> callback) {
        return queryAsync("", UserStoryDetail[].class).thenAccept(callback);
    }

    /**
     * Lists the user stories for the given project asynchronously.
     *
     * @param projectId project id
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listProjectUserStories(
            int projectId, Consumer<APIResponse<UserStoryDetail[]>> callback) {
        return queryAsync("?project=" + projectId, UserStoryDetail[].class).thenAccept(callback);
    }

    /**
     * Lists the user stories for the given milestone asynchronously.
     *
     * @param milestoneId milestone id
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listMilestoneUserStories(
            int milestoneId, Consumer<APIResponse<UserStoryDetail[]>> callback) {
        return queryAsync("?milestone=" + milestoneId, UserStoryDetail[].class)
                .thenAccept(callback);
    }

    /**
     * Gets the user story with the given ID asynchronously.
     *
     * @param userStoryId user story id
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStory(
            int userStoryId, Consumer<APIResponse<UserStoryDetail>> callback) {
        return queryAsync("/" + userStoryId, UserStoryDetail.class).thenAccept(callback);
    }

    /**
     * Gets the user story with the given reference and project asynchronously.
     *
     * @param userStoryRef user story reference
     * @param projectId project id
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryByRef(
            int userStoryRef, int projectId, Consumer<APIResponse<UserStoryDetail>> callback) {
        return queryAsync(
                        "/by_ref?ref=" + userStoryRef + "&project=" + projectId,
                        UserStoryDetail.class)
                .thenAccept(callback);
    }
}
