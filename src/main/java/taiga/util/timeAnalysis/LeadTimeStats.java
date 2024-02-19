package taiga.util.timeAnalysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import taiga.model.query.userstories.UserStoryInterface;

public class LeadTimeStats {
    private final Date date;
    private final List<UserStoryInterface> notCreated = new ArrayList<>();
    private final List<UserStoryInterface> inBacklog = new ArrayList<>();
    private final List<UserStoryInterface> inSprint = new ArrayList<>();
    private final List<UserStoryInterface> inProgress = new ArrayList<>();
    private final List<UserStoryInterface> inTest = new ArrayList<>();
    private final List<UserStoryInterface> inDone = new ArrayList<>();

    public LeadTimeStats(Date date){
        this.date = date;
    }

    /**
     * Adds the story to the stats, segmented correctly according to the Status given.
     * @param story The story to categorize
     * @param status the Status of the story, determines which list it gets added to.
     */
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

    /**
     * Get the list of User Stories which have not been created yet
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getNotCreated(){
        return notCreated;
    }

    /**
     * Get the list of User Stories which have been created but have not been assigned to a Sprint
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getInBacklog() {
        return inBacklog;
    }

    /**
     * Get the list of User Stories which have been assigned to a Sprint but have not been worked on at all
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getInSprint() {
        return inSprint;
    }

    /**
     * Get the list of User Stories which have in progress work
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getInProgress() {
        return inProgress;
    }

    /**
     * Get the list of User Stories which are in testing
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getInTest() {
        return inTest;
    }

    /**
     * Get the list of User Stories which have been completed
     * @return a List of UserStoryInterfaces
     */
    public List<UserStoryInterface> getInDone() {
        return inDone;
    }

    public Date getDate() {
        return date;
    }
}
