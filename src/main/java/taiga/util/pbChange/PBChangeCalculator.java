package taiga.util.pbChange;

import java.util.ArrayList;
import java.util.List;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.history.History;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.userstories.UserStoryInterface;

public class PBChangeCalculator {
    public static List<UserStoryInterface> filterUSAfterSprintStart(List<UserStoryInterface> userStories, Sprint sprint){
        List<UserStoryInterface> addedAfterStart = new ArrayList<>();
        for(UserStoryInterface userStory : userStories){
            if(userStory.getCreatedDate().after(sprint.getEstimatedStart())){
                addedAfterStart.add(userStory);
            }
        }
        return addedAfterStart;
    }

    public static List<EpicDetail> filterEpicAfterSprintStart(List<EpicDetail> epics, Sprint sprint){
        List<EpicDetail> addedAfterStart = new ArrayList<>();
        for(EpicDetail epic : epics){
            if(epic.getCreatedDate().after(sprint.getEstimatedStart())){
                addedAfterStart.add(epic);
            }
        }
        return addedAfterStart;
    }

    /**
     * Returns a list of the histories that were deleted after sprint start date.
     * IMPORTANT: Assumes using list of only userstory histories, see PBHelper
     * 
     * @param userStoryHistoryList The list of user story histories, see PBHelper
     * @param sprint The sprint start date being analyzed
     * @return A list of only those stories deleted/removed after start date
     */
    public static List<UserStoryDetail> filterUSRemovedAfterSprintStart(List<UserStoryDetail> userStories, Sprint sprint) {
        List<UserStoryDetail> removedAfterStart = new ArrayList<>();
        for (UserStoryDetail userStory : userStories) {
            if (userStory.getMilestone() != null) {
                if (userStory.getModifiedDate().after(sprint.getEstimatedStart())) {
                    removedAfterStart.add(userStory);
                }
            }
        }
        return removedAfterStart;
    }
}
