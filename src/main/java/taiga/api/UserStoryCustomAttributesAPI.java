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
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryCustomAttributeList(int project,
        Consumer<APIResponse<UserStoryCustomAttribute[]>> callback) {
            return queryAsync("?project=" + project, UserStoryCustomAttribute[].class).thenAccept(callback);
        }
        
}
