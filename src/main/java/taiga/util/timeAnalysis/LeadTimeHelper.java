package taiga.util.timeAnalysis;

import static taiga.util.UserStoryUtils.getLeadTimeForUserStory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.util.UserStoryUtils;

public class LeadTimeHelper {
    private List<LeadTimeEntry> leadTimeEntryList;

    UserStoryAPI userStoryAPI = new UserStoryAPI();
    public LeadTimeHelper(Project project) {
        this(project.getId());
    }

    public LeadTimeHelper(Integer projectId){
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(projectId, result ->{
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<UserStoryDetail> userStoryList = userStoryListReference.get();

        this.leadTimeEntryList = userStoryList
            .parallelStream()
            .map(UserStoryUtils::getLeadTimeForUserStory)
            .toList();
    }

    public LeadTimeStats getLeadTimeStatsForDate(Date date){
        LeadTimeStats stats = new LeadTimeStats();
        for(LeadTimeEntry entry : leadTimeEntryList){
            stats.addStory(entry.getUserStory(), entry.getStatusForDate(date));
        }
        return stats;
    }

    public static void main(String[] args) throws ParseException {
        LeadTimeHelper leadTimeHelper = new LeadTimeHelper(1521722);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LeadTimeStats leadTimeStats = leadTimeHelper.getLeadTimeStatsForDate(dateFormat.parse("2024-02-10T20:53:18.250Z"));
        int foo = 4;
    }
}
