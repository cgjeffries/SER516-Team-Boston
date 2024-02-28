package ui.metrics.pbHealth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.UserStoryDetail;

public class PBHealthHelper {

    private final List<UserStoryDetail> pbUserStories;

    UserStoryAPI userStoryAPI = new UserStoryAPI();
    public PBHealthHelper(Project project) {
        this(project.getId());
    }

    public PBHealthHelper(Integer projectId){
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(projectId, result ->{
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<UserStoryDetail> userStoryList = userStoryListReference.get();

        this.pbUserStories = userStoryList
            .stream()
            .filter(us -> us.getMilestone() == null)
            .toList();
    }

    public List<UserStoryDetail> getGroomedPB(){
        return pbUserStories
            .stream()
            .filter(us -> us.getStatusExtraInfo().getName().equalsIgnoreCase("Sprint-ready")) //TODO: test if these are the correct strings
            .toList();
    }

    public List<UserStoryDetail> getNotGroomedPB(){
        return pbUserStories
            .stream()
            .filter(us -> us.getStatusExtraInfo().getName().equalsIgnoreCase("New")) //TODO: test if these are the correct strings
            .toList();
    }
}
