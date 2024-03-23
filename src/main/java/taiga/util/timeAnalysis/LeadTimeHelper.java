package taiga.util.timeAnalysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.UserStoryUtils;

public class LeadTimeHelper {
    private final List<LeadTimeEntry> leadTimeEntryList;

    UserStoryAPI userStoryAPI = new UserStoryAPI();

    public LeadTimeHelper(Project project) {
        this(project.getId());
    }

    /**
     * Create a new LeadTimeHelper. Note that this does make a couple rounds of API Requests so there might be some delay here.
     *
     * @param projectId the integer Id of the Project whose User Stories we are analyzing the lead time of
     */
    public LeadTimeHelper(Integer projectId) {
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(projectId, result -> {
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<UserStoryDetail> userStoryList = userStoryListReference.get();

        this.leadTimeEntryList = userStoryList
                .parallelStream()
                .map(UserStoryUtils::getLeadTimeForUserStory)
                .toList();
    }

    /**
     * Return the List of LeadTimeEntries for external processing
     *
     * @return a List of LeadTimeEntries
     */
    public List<LeadTimeEntry> getLeadTimeEntryList() {
        return new ArrayList<>(leadTimeEntryList);
    }

    /**
     * Given a date, return a LeadTimeStats which gives the user stories at each status level for that date
     *
     * @param date the date we want the stats for
     * @return a LeadTimeStats object
     */
    public LeadTimeStats getLeadTimeStatsForDate(Date date) {
        LeadTimeStats stats = new LeadTimeStats(date);
        for (LeadTimeEntry entry : leadTimeEntryList) {
            stats.addStory(entry.getUserStory(), entry.getStatusForDate(date));
        }
        return stats;
    }

    /**
     * Get all lead times for the User Stories in the Project
     *
     * @return a List of LeadTimeStoryEntry's
     */
    public List<LeadTimeStoryEntry> getAllStoryLeadTimes() {
        return leadTimeEntryList
                .stream()
                .map(entry -> {
                    UserStoryInterface story = entry.getUserStory();
                    if (story.getFinishDate() != null) {
                        return new LeadTimeStoryEntry(story, story.getCreatedDate(), story.getFinishDate());
                    }
                    return new LeadTimeStoryEntry(story, story.getCreatedDate(), null, false);
                })
                .toList();
    }
}
