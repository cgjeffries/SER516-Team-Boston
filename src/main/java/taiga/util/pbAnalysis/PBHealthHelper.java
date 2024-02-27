package taiga.util.pbAnalysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.UserStoryDetail;

public class PBHealthHelper {

    private final List<PBHealthEntry> pbHealthEntryList;

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

        this.pbHealthEntryList = userStoryList
            .parallelStream()
            .map(this::createPBHealthEntryForUserStory) // TO DO!
            .toList();
    }

    /**
     * Given a date, return a PBHealthStats which gives the user stories in each phase for that date
     * @param date the date we want the stats for
     * @return a PBHealthStats object
     */
    public PBHealthStats getPBHealthStatsForDate(Date date){
        PBHealthStats stats = new PBHealthStats(date);
        for(PBHealthEntry entry : pbHealthEntryList){
            stats.addStory(entry.getUserStory(), entry.getPbStatusForDate(date)); // Implement getPhaseForDate
        }
        return stats;
    }

    // TO DO, placeholder for the method to create PBHealthEntry for a UserStory
    private PBHealthEntry createPBHealthEntryForUserStory(UserStoryDetail detail) {
        // depends on how you track the phases of a user story?
        // analyze the history of status changes?
        return null;
    }
}
