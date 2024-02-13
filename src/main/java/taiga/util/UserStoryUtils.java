package taiga.util;

import taiga.api.UserStoryCustomAttributesAPI;
import taiga.api.UserStoryCustomAttributesValuesAPI;
import taiga.model.query.customattributes.UserStoryCustomAttribute;
import taiga.model.query.customattributes.UserStoryCustomAttributesValues;
import taiga.model.query.sprint.UserStory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class for User Stories current functionality: extract BV, .
 */
public class UserStoryUtils {

    private static final UserStoryCustomAttributesAPI userStoryCustomAttributesAPI = new UserStoryCustomAttributesAPI();
    private static final UserStoryCustomAttributesValuesAPI userStoryCustomAttributesValuesAPI = new UserStoryCustomAttributesValuesAPI();
    private static final HashMap<Integer, UserStoryCustomAttribute> userStoryCustomAttributes = new HashMap<>();

    private static UserStoryCustomAttribute loadBvAttribute(int projectId) {
        AtomicReference<List<UserStoryCustomAttribute>> attributes = new AtomicReference<>();
        userStoryCustomAttributesAPI.getUserStoryCustomAttributeList(projectId, results -> {
            attributes.set(Arrays.asList(results.getContent()));
        }).join();

        for (UserStoryCustomAttribute attribute : attributes.get()) {
            if (attribute.getName().equals("BV")) {
                return attribute;
            }
        }

        throw new RuntimeException("Project doesn't have a business value custom attribute that matches the pattern!");
    }

    /**
     * Gets the business value (in the form of a custom attribute) of a user story
     *
     * @param story The user story to extract business value from
     * @return The business value of the user story
     */
    public static Double getBusinessValueForUserStory(UserStory story) {
        if (!userStoryCustomAttributes.containsKey(story.getProject())) {
            try {
                userStoryCustomAttributes.put(story.getProject(), loadBvAttribute(story.getProject()));
            } catch (RuntimeException e) {
                return 0d;
            }
        }

        UserStoryCustomAttribute bvAttribute = userStoryCustomAttributes.get(story.getProject());

        AtomicReference<UserStoryCustomAttributesValues> values = new AtomicReference<>();
        userStoryCustomAttributesValuesAPI.getUserStoryCustomAttributeValue(story.getId(), result -> {
            values.set(result.getContent());
        }).join();

        String bvString = values.get().getAttributesValues().get(String.valueOf(bvAttribute.getId()));

        double bv = 0.0;

        try {
            bv = Double.parseDouble(bvString);
        } catch (NumberFormatException e) {
            System.out.println("Business value attribute had non-number value " + bvString + " on User Story " + story.getId() + "!");
        } catch (Exception e) {
            System.out.println("Exception during parsing BV attribute in US " + story.getId());
        }

        return bv;
    }
}