package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import taiga.model.query.customattributes.UserStoryCustomAttribute;

public class UserStoryCustomAttributesAPI extends APIWrapperBase{

    public UserStoryCustomAttributesAPI() {
        super("userstory-custom-attributes");
    }

    /**
     * Retrieves the list of custom attributes of a user story
     *
     * @return A CompletableFuture containing the list of user stories
     */
    public CompletableFuture<Void> getUserStoryCustomAttributeList(
        Consumer<APIResponse<UserStoryCustomAttribute[]>> callback) {
            return queryAsync("", UserStoryCustomAttribute[].class).thenAccept(callback);
        }
        
}
