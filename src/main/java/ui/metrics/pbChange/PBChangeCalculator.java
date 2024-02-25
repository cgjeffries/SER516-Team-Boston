package ui.metrics.pbchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import settings.Settings;
import taiga.api.UserStoryAPI;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.userstories.UserStoryInterface;

public class PBChangeCalculator {
    private static List<UserStoryDetail> userStories;

    private UserStoryAPI userStoryAPI = new UserStoryAPI();

    public PBChangeCalculator() {
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(Settings.get().getAppModel().getCurrentProject().get().getId(), result -> {
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        userStories = userStoryListReference.get();
    }

    public static List<UserStoryInterface> filterUSAfterSprintStart(Sprint sprint){
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

    public static List<UserStoryDetail> filterUSRemovedAfterSprintStart(Sprint sprint) {
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
