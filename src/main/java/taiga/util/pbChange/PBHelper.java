package taiga.util.pbChange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import taiga.api.SprintAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;

public class PBHelper {

    List<Sprint> sprintList;
    List<UserStoryDetail> userStoryList;
    UserStoryAPI userStoryAPI = new UserStoryAPI();
    SprintAPI sprintAPI = new SprintAPI();
    
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

    }


}

