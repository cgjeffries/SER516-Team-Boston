package ui.metrics.pbChange;

import java.util.ArrayList;
import java.util.List;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
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
