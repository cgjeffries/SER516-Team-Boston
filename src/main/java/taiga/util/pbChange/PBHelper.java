package taiga.util.pbChange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import settings.Settings;
import taiga.api.EpicsAPI;
import taiga.api.SprintAPI;
import taiga.api.UserStoryAPI;
import taiga.api.UserStoryHistoryAPI;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;

public class PBHelper {

    private List<Sprint> sprintList;
    private List<UserStoryDetail> userStoryList;
    private List<EpicDetail> epicList;
    private List<ItemHistory> userStoryHistoryList = new ArrayList<>();

    UserStoryAPI userStoryAPI = new UserStoryAPI();
    SprintAPI sprintAPI = new SprintAPI();
    EpicsAPI epicAPI = new EpicsAPI();
    UserStoryHistoryAPI historyAPI = new UserStoryHistoryAPI();
    
    private int projectId;

    public PBHelper() {
        sprintList = new ArrayList<>();
        userStoryList = new ArrayList<>();
        epicList = new ArrayList<>();
        userStoryHistoryList = new ArrayList<>();
        projectId = Settings.get().getAppModel().getCurrentProject().get().getId();
    }

    public void fetchUserStoryList() {
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(projectId, result -> {
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        userStoryList = userStoryListReference.get();
    }

    public void fetchSprintList() {
        AtomicReference<List<Sprint>> sprintListReference = new AtomicReference<>();
        sprintAPI.listSprints(projectId, result -> {
            sprintListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        sprintList = sprintListReference.get();
    }

    public void fetchEpicList() {
        AtomicReference<List<EpicDetail>> epicListReference = new AtomicReference<>();
        epicAPI.listEpics(result -> {
            epicListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        epicList = epicListReference.get();
    }

    public void fetchUnitStoryHistory() {
        if (userStoryList.isEmpty()) {
            fetchUserStoryList();
        }
        for (UserStoryDetail userStory : userStoryList) {
            AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
            historyAPI.getUserStoryHistory(userStory.getId(), result -> {
                historyListReference.set(new ArrayList<>(List.of(result.getContent())));
            }).join();

            userStoryHistoryList.addAll(historyListReference.get());
        }

    }

    public List<Sprint> getSprintList() {
        return sprintList;
    }

    public List<UserStoryDetail> getUserStoryList() {
        return userStoryList;
    }

    public List<EpicDetail> getEpicList() {
        return epicList;
    }

    public List<ItemHistory> getUserStoryHistoryList() {
        return userStoryHistoryList;
    }

}
