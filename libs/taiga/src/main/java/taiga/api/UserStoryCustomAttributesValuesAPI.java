package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.customattributes.UserStoryCustomAttributesValues;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserStoryCustomAttributesValuesAPI extends APIWrapperBase {

    public UserStoryCustomAttributesValuesAPI() {
        super("userstories/custom-attributes-values/");
    }


    /**
     * Retrieves the list of custom attribute values of a user story
     *
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getUserStoryCustomAttributeValue(Integer customAttributeId,
                                                                    Consumer<APIResponse<UserStoryCustomAttributesValues>> callback) {
        return queryAsync(customAttributeId.toString(), UserStoryCustomAttributesValues.class).thenAccept(callback);
    }

}
