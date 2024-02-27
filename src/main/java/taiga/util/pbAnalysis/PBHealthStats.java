package taiga.util.pbAnalysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import taiga.model.query.userstories.UserStoryInterface;

public class PBHealthStats {

    private final Date date;
    private final List<UserStoryInterface> inNew = new ArrayList<>();
    private final List<UserStoryInterface> inProgress = new ArrayList<>();
    private final List<UserStoryInterface> inTesting = new ArrayList<>();
    private final List<UserStoryInterface> inDone = new ArrayList<>();

    public PBHealthStats(Date date){
        this.date = date;
    }

    /**
     * Adds the story to the stats, according to its current phase.
     * @param story The story to categorize
     * @param phase The current phase of the story.
     */
    public void addStory(UserStoryInterface story, PBHealthEntry.Status phase){
        switch(phase){
            case NEW:
                inNew.add(story);
                break;
            case IN_PROGRESS:
                inProgress.add(story);
                break;
            case TESTING:
                inTesting.add(story);
                break;
            case DONE:
                inDone.add(story);
                break;
        }
    }

    // Getters for each story list
    public List<UserStoryInterface> getInNew() { return inNew; }
    public List<UserStoryInterface> getInProgress() { return inProgress; }
    public List<UserStoryInterface> getInTesting() { return inTesting; }
    public List<UserStoryInterface> getInDone() { return inDone; }
    public Date getDate() { return date; }
}
