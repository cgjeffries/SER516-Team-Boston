package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.customattributes.UserStoryCustomAttributesValues;

public class UserStoryCustomAttributesValuesAPI extends APIWrapperBase{

    public UserStoryCustomAttributesValuesAPI() {
        super("userstories/custom-attributes-values");
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
