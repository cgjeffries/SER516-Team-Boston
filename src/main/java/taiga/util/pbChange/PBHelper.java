package taiga.util.pbChange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import taiga.api.EpicsAPI;
import taiga.api.SprintAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;

public class PBHelper {

    private List<Sprint> sprintList;
    private List<UserStoryDetail> userStoryList;
    private List<EpicDetail> epicList;

    UserStoryAPI userStoryAPI = new UserStoryAPI();
    SprintAPI sprintAPI = new SprintAPI();
    EpicsAPI epicAPI = new EpicsAPI();
    
    public PBHelper(Integer projectId, Integer sprintID) {
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(projectId, result ->{
                userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        userStoryList = userStoryListReference.get();
            
        AtomicReference<List<Sprint>> sprintListReference = new AtomicReference<>();
        sprintAPI.listSprints(projectId, result ->{
            sprintListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        sprintList = sprintListReference.get();

        AtomicReference<List<EpicDetail>> epicListReference = new AtomicReference<>();
        epicAPI.listEpics( result ->{
            epicListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        epicList = epicListReference.get();

    }

    public List<Sprint> getSprintList() {
        return sprintList;
    }

    public void setSprintList(List<Sprint> sprintList) {
        this.sprintList = sprintList;
    }

    public List<UserStoryDetail> getUserStoryList() {
        return userStoryList;
    }

    public void setUserStoryList(List<UserStoryDetail> userStoryList) {
        this.userStoryList = userStoryList;
    }

    public List<EpicDetail> getEpicList() {
        return epicList;
    }

    public void setEpicList(List<EpicDetail> epicList) {
        this.epicList = epicList;
    }

}

