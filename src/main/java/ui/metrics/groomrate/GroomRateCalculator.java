package ui.metrics.groomrate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.util.timeAnalysis.LeadTimeEntry;
import taiga.util.timeAnalysis.LeadTimeHelper;

public class GroomRateCalculator {

    private List<UserStoryDetail> userStories;
    private final UserStoryAPI userStoryAPI;

    private LeadTimeHelper leadTimeHelper;

    public GroomRateCalculator(int projectId) {
        this.userStoryAPI = new UserStoryAPI();
        leadTimeHelper = new LeadTimeHelper(projectId);
    }

    public List<GroomRateItem> calculate(Sprint sprint) {
        return calculate(sprint.getEstimatedStart(), sprint.getEstimatedFinish());
    }

    public List<GroomRateItem> calculate(Date startDate, Date endDate) {

        List<LeadTimeEntry> leadTimeEntries = leadTimeHelper.getLeadTimeEntryList()
            .stream()
            .filter(leadTimeEntry -> {
                // Filter by user stories that weren't already Done before the start of the time period
                return !leadTimeEntry.getStatusForDate(startDate).equals(LeadTimeEntry.Status.DONE);
            })
            .toList();

        List<GroomRateItem> modifiedStories = leadTimeEntries
            .stream()
            .map(leadTimeEntry -> {
                List<ItemHistory> historyList = leadTimeEntry.getHistoryList()
                    .stream()
                    .filter(historyItem -> historyItem.getCreatedAt().after(startDate) && historyItem.getCreatedAt().before(endDate))
                    .toList();

                return new GroomRateItem(historyList, leadTimeEntry.getUserStory());
            })
            .toList();

        return modifiedStories;
    }
}
