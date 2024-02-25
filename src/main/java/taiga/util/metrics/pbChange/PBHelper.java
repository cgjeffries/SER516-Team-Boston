package taiga.util.metrics.pbChange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import settings.Settings;
import taiga.api.EpicsAPI;
import taiga.api.HistoryAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.history.History;
import taiga.model.query.sprint.UserStoryDetail;

public class PBHelper {

    private List<UserStoryDetail> userStoryList;
    private List<EpicDetail> epicList;
    private List<History> userStoryHistoryList = new ArrayList<>();

    UserStoryAPI userStoryAPI = new UserStoryAPI();
    EpicsAPI epicAPI = new EpicsAPI();
    HistoryAPI historyAPI = new HistoryAPI();
    
    private int projectId;

    public PBHelper() {
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
            AtomicReference<List<History>> historyListReference = new AtomicReference<>();
            historyAPI.getUserStoryHistory(userStory.getId(), result -> {
                historyListReference.set(new ArrayList<>(List.of(result.getContent())));
            }).join();

            userStoryHistoryList.addAll(historyListReference.get());
        }

    }

    public List<UserStoryDetail> getUserStoryList() {
        return userStoryList;
    }

    public List<EpicDetail> getEpicList() {
        return epicList;
    }

    public List<History> getUserStoryHistoryList() {
        return userStoryHistoryList;
    }

}
