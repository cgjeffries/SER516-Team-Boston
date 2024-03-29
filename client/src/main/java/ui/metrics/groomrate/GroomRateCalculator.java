package ui.metrics.groomrate;

import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStoryDetail;
import taiga.models.taskhistory.ItemHistory;
import taigaold.util.timeAnalysis.LeadTimeEntry;
import taigaold.util.timeAnalysis.LeadTimeHelper;

import java.util.Date;
import java.util.List;

public class GroomRateCalculator {

    private List<UserStoryDetail> userStories;

    private final LeadTimeHelper leadTimeHelper;

    public GroomRateCalculator(int projectId) {
        this(new LeadTimeHelper(projectId));
    }

    public GroomRateCalculator(LeadTimeHelper leadTimeHelper) {
        this.leadTimeHelper = leadTimeHelper;
    }

    /**
     * Calculate and return a List of GroomRateItems for the specified time period. GroomRateItems
     * will only be included for UserStories which were not Done on the Start Date. Modifications
     * in each GroomRateItems will also already be filtered to only include those which were done
     * in the specified time window.
     *
     * @param sprint the sprint time window to examine
     * @return a List of GroomRateItems
     */
    public List<GroomRateItem> calculate(Sprint sprint) {
        return calculate(sprint.getEstimatedStart(), sprint.getEstimatedFinish());
    }

    /**
     * Calculate and return a List of GroomRateItems for the specified time period. GroomRateItems
     * will only be included for UserStories which were not Done on the Start Date. Modifications
     * in each GroomRateItems will also already be filtered to only include those which were done
     * in the specified time window.
     *
     * @param startDate The start of the time window to examine
     * @param endDate   The end of the time window to examine
     * @return a List of GroomRateItems
     */
    public List<GroomRateItem> calculate(Date startDate, Date endDate) {

        //get leadTimeEntries for all the user stories in the project (re-using leadTimeEntries will
        //save us a lot of time and effort here because they can get the state of a US at any Date.
        List<LeadTimeEntry> leadTimeEntries = leadTimeHelper.getLeadTimeEntryList()
                .stream()
                .filter(leadTimeEntry -> {
                    // Filter by user stories that weren't already Done before the start of the time period
                    return !leadTimeEntry.getStatusForDate(startDate).equals(LeadTimeEntry.Status.DONE);
                })
                .filter(leadTimeEntry -> {
                    // Filter by user stories that were created before the end of the time period
                    return leadTimeEntry.getUserStory().getCreatedDate().before(endDate);
                })
                .toList();

        //Create the list of GroomRateItems
        List<GroomRateItem> modifiedStories = leadTimeEntries
                .stream()
                //Convert to GroomRateItems
                .map(leadTimeEntry -> {
                    //Get all the history entries for the current US
                    List<ItemHistory> historyList = leadTimeEntry.getHistoryList()
                            .stream()
                            //Filter by history entries that happened within our time period
                            .filter(historyItem -> historyItem.getCreatedAt().after(startDate) && historyItem.getCreatedAt().before(endDate))
                            .toList();

                    //Greate a new GroomRateItem with the data. The GroomRateItem itself will automatically
                    // calculate if the US was modified or not given the historyList
                    return new GroomRateItem(historyList, leadTimeEntry.getUserStory());
                })
                .toList();

        return modifiedStories;
    }
}
