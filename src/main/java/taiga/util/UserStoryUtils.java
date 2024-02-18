package taiga.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import taiga.api.UserStoryAPI;
import taiga.api.UserStoryCustomAttributesAPI;
import taiga.api.UserStoryCustomAttributesValuesAPI;
import taiga.api.UserStoryHistoryAPI;
import taiga.model.query.customattributes.UserStoryCustomAttribute;
import taiga.model.query.customattributes.UserStoryCustomAttributesValues;
import taiga.model.query.sprint.UserStory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.timeAnalysis.CycleTimeEntry;
import taiga.util.timeAnalysis.LeadTimeEntry;

/**
 * Utility class for User Stories current functionality: extract BV, .
 */
public class UserStoryUtils {

    private static final UserStoryCustomAttributesAPI userStoryCustomAttributesAPI = new UserStoryCustomAttributesAPI();
    private static final UserStoryCustomAttributesValuesAPI userStoryCustomAttributesValuesAPI = new UserStoryCustomAttributesValuesAPI();
    private static final UserStoryHistoryAPI userStoryHistoryAPI = new UserStoryHistoryAPI();
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

    public static CycleTimeEntry getCycleTimeForUserStory(UserStory story){
        return getCycleTimeForUserStory(story.getId());
    }

    public static CycleTimeEntry getCycleTimeForUserStory(UserStoryDetail storyDetail){
        return getCycleTimeForUserStory(storyDetail.getId());
    }

    public static CycleTimeEntry getCycleTimeForUserStory(int storyId){
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        userStoryHistoryAPI.getUserStoryHistory(storyId, result ->{
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        //get first time moved to "In Progress"
        Date startDate = null;
        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if(valuesDiff.getStatus() == null){
                continue;
            }

            if(valuesDiff.getStatus()[1].equalsIgnoreCase("In progress")){
                startDate = history.getCreatedAt();
                break;
            }
        }

        if(startDate == null){
            return new CycleTimeEntry(null, null);
        }

        //get last time moved to "Done"
        Collections.reverse(historyList);
        Date endDate = null;
        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if(valuesDiff.getStatus() == null){
                continue;
            }

            if(valuesDiff.getStatus()[1].equalsIgnoreCase("Done")){
                endDate = history.getCreatedAt();
                break;
            }
        }

        if(endDate == null){
            return new CycleTimeEntry(startDate, null);
        }

        return new CycleTimeEntry(startDate, endDate);
    }

    public static LeadTimeEntry getLeadTimeForUserStory(UserStory story){
        return getLeadTimeForUserStory(story, story.getCreatedDate());
    }

    public static LeadTimeEntry getLeadTimeForUserStory(UserStoryDetail storyDetail){
        return getLeadTimeForUserStory(storyDetail, storyDetail.getCreatedDate());
    }

    private static LeadTimeEntry getLeadTimeForUserStory(UserStoryInterface story, Date createdDate){
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        userStoryHistoryAPI.getUserStoryHistory(story.getId(), result ->{
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        return new LeadTimeEntry(historyList, story);
    }

    //TODO: remove test code
    public static void main(String[] args){
        AtomicReference<UserStoryDetail> userStoryDetail = new AtomicReference<>();
        (new UserStoryAPI()).getUserStory(5468121, result ->{
            userStoryDetail.set(result.getContent());
        }).join();

        CycleTimeEntry time = getCycleTimeForUserStory(userStoryDetail.get());
    }
}