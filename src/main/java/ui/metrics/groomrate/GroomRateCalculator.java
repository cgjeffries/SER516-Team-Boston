package ui.metrics.groomrate;

import java.util.ArrayList;
import java.util.List;

import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;

public class GroomRateCalculator {

    private List<UserStoryDetail> userStories;
    private final UserStoryAPI userStoryAPI;

    public GroomRateCalculator() {
        this.userStoryAPI = new UserStoryAPI();
    }

    private List<UserStoryDetail> getUserStories(int projectId) {
        userStoryAPI.listProjectUserStories(projectId, result -> {
            userStories = new ArrayList<>(List.of(result.getContent()));
        }).join();
        return userStories;
    }

    public List<GroomRateItem> calculate(Project project, Sprint sprint) {
        return calculate(project.getId(), sprint);
    }
    
    public List<GroomRateItem> calculate(int projectId, Sprint sprint) {
        List<UserStoryDetail> stories = getUserStories(projectId);

        List<GroomRateItem> modifiedStories = stories
                .parallelStream()
                .filter(s -> s.getModifiedDate().after(sprint.getEstimatedStart()))
                .map(s -> new GroomRateItem(s.getModifiedDate(), s, true))
                .toList();

        return modifiedStories;
    }
}
