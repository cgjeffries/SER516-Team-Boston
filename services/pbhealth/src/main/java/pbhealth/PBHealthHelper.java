package pbhealth;

import taiga.TaigaClient;
import taiga.models.project.Project;
import taiga.models.sprint.UserStoryDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PBHealthHelper {

    private final List<UserStoryDetail> pbUserStories;

    public PBHealthHelper(Project project) {
        this(project.getId());
    }

    public PBHealthHelper(Integer projectId) {
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        TaigaClient.getUserStoryAPI().listProjectUserStories(projectId, result -> {
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<UserStoryDetail> userStoryList = userStoryListReference.get();

        this.pbUserStories = userStoryList
                .stream()
                .filter(us -> us.getMilestone() == null)
                .toList();
    }

    public List<UserStoryDetail> getGroomedPB() {
        return pbUserStories
                .stream()
                .filter(us -> us.getStatusExtraInfo().getName().equalsIgnoreCase("Sprint-ready")) //TODO: test if these are the correct strings
                .toList();
    }

    public List<UserStoryDetail> getNotGroomedPB() {
        return pbUserStories
                .stream()
                .filter(us -> us.getStatusExtraInfo().getName().equalsIgnoreCase("New")) //TODO: test if these are the correct strings
                .toList();
    }
}
