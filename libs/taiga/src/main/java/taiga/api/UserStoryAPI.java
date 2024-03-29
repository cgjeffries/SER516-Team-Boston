package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.customattributes.UserStoryCustomAttributesValues;
import taiga.models.sprint.UserStoryDetail;
import taiga.models.userstories.UserStory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
     * @param callback  Consumer function to execute upon receiving query result.
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
     * @param callback    Consumer function to execute upon receiving query result.
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
     * @param callback    Consumer function to execute upon receiving query result.
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
     * @param projectId    project id
     * @param callback     Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryByRef(
            int userStoryRef, int projectId, Consumer<APIResponse<UserStoryDetail>> callback) {
        return queryAsync(
                "/by_ref?ref=" + userStoryRef + "&project=" + projectId,
                UserStoryDetail.class)
                .thenAccept(callback);
    }

    /**
     * Retrieves the total points for a specific user story asynchronously.
     *
     * @param userStoryId The ID of the user story.
     * @param callback    Consumer function to execute upon receiving the query result.
     * @return A CompletableFuture containing the total points for the user story.
     */
    public CompletableFuture<Double> getUserStoryTotalPoints(
            int userStoryId, Consumer<APIResponse<UserStory>> callback) {
        return queryAsync("/" + userStoryId, UserStory.class)
                .thenApply(response -> {
                    if (response.getStatus() == 200) {
                        UserStory userStory = response.getContent();
                        return userStory != null ? userStory.getTotalPoints() : 0.0;
                    } else {
                        return 0.0;
                    }
                })
                .whenComplete((result, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                    }
                });
    }

    /**
     * Retrieves the list of custom attributes of a user story
     *
     * @param attributeID The ID of the user story attribute
     * @param callback    Consumer function to execute upon receiving the query result.
     * @return A CompletableFuture containing the list of user stories
     */
    public CompletableFuture<Void> getUserStoryCustomAttributeValues(
            int attributeID, Consumer<APIResponse<UserStoryCustomAttributesValues>> callback) {
        return queryAsync("/custom-attributes-values/" + attributeID, UserStoryCustomAttributesValues.class).thenAccept(callback);
    }
}

