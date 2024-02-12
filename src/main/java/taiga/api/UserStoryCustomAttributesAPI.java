package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import taiga.model.query.sprint.UserStoryDetail;

public class UserStoryCustomAttributesAPI extends APIWrapperBase{

    public UserStoryCustomAttributesAPI() {
        super("userstory-custom-attributes");
    }

    /**
     * Retrieves the list of custom attributes of a user story
     *
     * @return A CompletableFuture containing the list of user stories
     */
    public CompletableFuture<> getUserStoryCustomAttributeList(
        Consumer<APIResponse<UserStoryDetail>> callback) {
            return queryAsync(getAuthToken(), null).thenAccept(callback);
        }
        
}
