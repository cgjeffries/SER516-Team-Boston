package taiga.util.timeAnalysis;

import java.util.ArrayList;
import java.util.List;
import taiga.model.query.userstories.UserStoryInterface;

public class LeadTimeStats {
    private List<UserStoryInterface> notCreated = new ArrayList<>();
    private List<UserStoryInterface> inBacklog = new ArrayList<>();
    private List<UserStoryInterface> inSprint = new ArrayList<>();
    private List<UserStoryInterface> inProgress = new ArrayList<>();
    private List<UserStoryInterface> inTest = new ArrayList<>();
    private List<UserStoryInterface> inDone = new ArrayList<>();

    public void addStory(UserStoryInterface story, LeadTimeEntry.Status status){
        switch(status){
            case NOT_CREATED:
                notCreated.add(story);
                break;
            case BACKLOG:
                inBacklog.add(story);
                break;
            case IN_SPRINT:
                inSprint.add(story);
                break;
            case IN_PROGRESS:
                inProgress.add(story);
                break;
            case READY_FOR_TEST:
                inTest.add(story);
                break;
            case DONE:
                inDone.add(story);
        }
    }

    public List<UserStoryInterface> getNotCreated(){
        return notCreated;
    }

    public List<UserStoryInterface> getInBacklog() {
        return inBacklog;
    }

    public List<UserStoryInterface> getInSprint() {
        return inSprint;
    }

    public List<UserStoryInterface> getInProgress() {
        return inProgress;
    }

    public List<UserStoryInterface> getInTest() {
        return inTest;
    }

    public List<UserStoryInterface> getInDone() {
        return inDone;
    }
}
