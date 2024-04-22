package burndown.util;

import taiga.TaigaClient;
import taiga.models.customattributes.UserStoryCustomAttribute;
import taiga.models.customattributes.UserStoryCustomAttributesValues;
import taiga.models.userstories.UserStoryInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BVUtil {
    private static final HashMap<Integer, UserStoryCustomAttribute> userStoryCustomAttributes = new HashMap<>();

    /**
     * Gets the Attribute of the UserStory which contains the Business Value. This si tricky because
     * Business value is not natively supported by Taiga, so we have to parse the custom attributes
     * and find the one that refers to Business Value specifically, and return it
     *
     * @param projectId the Id of the project that we are working in
     * @return A UserStoryCustomAttribute which is the Business Value attribute in this project.
     */
    private static UserStoryCustomAttribute loadBvAttribute(int projectId) {
        AtomicReference<List<UserStoryCustomAttribute>> attributes = new AtomicReference<>();
        TaigaClient.getUserStoryCustomAttributesAPI().getUserStoryCustomAttributeList(projectId, results -> {
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
    public static Double getBusinessValueForUserStory(UserStoryInterface story) {
        if (!userStoryCustomAttributes.containsKey(story.getProject())) {
            try {
                userStoryCustomAttributes.put(story.getProject(), loadBvAttribute(story.getProject()));
            } catch (RuntimeException e) {
                return 0d;
            }
        }

        UserStoryCustomAttribute bvAttribute = userStoryCustomAttributes.get(story.getProject());

        AtomicReference<UserStoryCustomAttributesValues> values = new AtomicReference<>();
        TaigaClient.getUserStoryCustomAttributesValuesAPI().getUserStoryCustomAttributeValue(story.getId(), result -> {
            values.set(result.getContent());
        }).join();

        String bvString = values.get().getAttributesValues().get(String.valueOf(bvAttribute.getId()));

        double bv = 0.0;

        if (bvString != null) {
            try {
                bv = Double.parseDouble(bvString);
            } catch (NumberFormatException e) {
                System.out.println("Business value attribute had non-number value " + bvString +
                        " on User Story " + story.getId() + "!");
            } catch (Exception e) {
                System.out.println("Exception during parsing BV attribute in US " + story.getId());
                e.printStackTrace();
            }
        }

        return bv;
    }
}
