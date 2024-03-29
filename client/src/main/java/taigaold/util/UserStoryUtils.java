package taigaold.util;

import taiga.TaigaClient;
import taiga.models.customattributes.UserStoryCustomAttribute;
import taiga.models.customattributes.UserStoryCustomAttributesValues;
import taiga.models.taskhistory.ItemHistory;
import taiga.models.taskhistory.ItemHistoryValuesDiff;
import taiga.models.userstories.UserStoryInterface;
import taigaold.util.timeAnalysis.CycleTimeEntry;
import taigaold.util.timeAnalysis.LeadTimeEntry;
import ui.tooltips.CycleTimeUserStoryTooltip;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class for User Stories current functionality: extract BV, .
 */
public class UserStoryUtils {

    //    private static final UserStoryCustomAttributesAPI userStoryCustomAttributesAPI = new UserStoryCustomAttributesAPI();
//    private static final UserStoryCustomAttributesValuesAPI userStoryCustomAttributesValuesAPI = new UserStoryCustomAttributesValuesAPI();
//    private static final UserStoryHistoryAPI userStoryHistoryAPI = new UserStoryHistoryAPI();
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

    /**
     * Gets the cycle time for the specified UserStory
     *
     * @param story any UserStory object, including UserStoryDetail etc.
     * @return a CycleTimeEntry for the specified UserStory
     */
    public static CycleTimeEntry<UserStoryInterface> getCycleTimeForUserStory(UserStoryInterface story) {
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        TaigaClient.getUserStoryHistoryAPI().getUserStoryHistory(story.getId(), result -> {
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        /*
        Note here that we take the *first* time the story is moved to in progress and the *last*
        time that it is moved to Done. This is intentional because regardless of the Scrum Rules,
        people may move a task to In progress and back to New multiple times, and they may also move
        the task out of done after placing it there. Therefore we choose to take the time between
        the first move to In Progress and the last time the Story was moved to Done.
         */

        //get first time moved to "In Progress"
        Date startDate = null;
        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if (valuesDiff.getStatus() == null) {
                continue;
            }

            if (valuesDiff.getStatus()[1].equalsIgnoreCase("In progress")) {
                startDate = history.getCreatedAt();
                break;
            }
        }

        if (startDate == null) {
            CycleTimeEntry<UserStoryInterface> entry = new CycleTimeEntry<>(null, null, null);
            entry.setTooltipCallback(new CycleTimeUserStoryTooltip());
            return entry;
        }

        //get last time moved to "Done"
        Collections.reverse(historyList);
        Date endDate = null;
        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if (valuesDiff.getStatus() == null) {
                continue;
            }

            if (valuesDiff.getStatus()[1].equalsIgnoreCase("Done")) {
                endDate = history.getCreatedAt();
                break;
            }
        }

        if (endDate == null || !story.getIsClosed()) {
            CycleTimeEntry<UserStoryInterface> entry = new CycleTimeEntry<>(null, startDate, null, false);
            entry.setTooltipCallback(new CycleTimeUserStoryTooltip());
            return entry;
        }

        CycleTimeEntry<UserStoryInterface> entry = new CycleTimeEntry<>(story, startDate, endDate);
        entry.setTooltipCallback(new CycleTimeUserStoryTooltip());
        return entry;
    }

    /**
     * Gets a LeadTimeEntry for the specified UserStory. Usually this is not called directly,
     * instead you might want to consider using the LeadTimeHelper.
     *
     * @param story any UserStory object, including UserStoryDetail
     * @return a LeadTimeEntry for this UserStory
     */
    public static LeadTimeEntry getLeadTimeForUserStory(UserStoryInterface story) {
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        TaigaClient.getUserStoryHistoryAPI().getUserStoryHistory(story.getId(), result -> {
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        return new LeadTimeEntry(historyList, story);
    }
}