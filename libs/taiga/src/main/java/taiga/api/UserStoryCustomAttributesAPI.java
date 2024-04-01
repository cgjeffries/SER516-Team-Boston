package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.customattributes.UserStoryCustomAttribute;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserStoryCustomAttributesAPI extends APIWrapperBase {

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
