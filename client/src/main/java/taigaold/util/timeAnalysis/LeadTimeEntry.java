package taigaold.util.timeAnalysis;

import taiga.models.taskhistory.ItemHistory;
import taiga.models.taskhistory.ItemHistoryValuesDiff;
import taiga.models.userstories.UserStoryInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LeadTimeEntry {

    private final List<ItemHistory> historyList;
    private final UserStoryInterface userStory;

    /**
     * Enum for representing the states that a UserStory can be in
     */
    public enum Status {
        NOT_CREATED,
        BACKLOG,
        IN_SPRINT,
        IN_PROGRESS,
        READY_FOR_TEST,
        DONE
    }

    /**
     * Create a new LeadTimeEntry.
     *
     * @param historyList the list of ItemHistory objects for the associated UserStory
     * @param userStory   the UserStory associated with the ItemHistory list that we want to be abel to get the status for
     */
    public LeadTimeEntry(List<ItemHistory> historyList, UserStoryInterface userStory) {
        this.historyList = historyList;
        Collections.sort(this.historyList);
        this.userStory = userStory;
    }

    /**
     * Computes the status of the US on the given date from the History data of the US. Doesn't make any API calls.
     *
     * @param date the date to compute the status for
     * @return a Status enum describing the status of the US at the specified date
     */
    public Status getStatusForDate(Date date) {
        if (date.before(userStory.getCreatedDate())) {
            return Status.NOT_CREATED;
        }

        Status lastStatus = Status.BACKLOG;

        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();

            if (history.getCreatedAt().after(date)) {
                break;
            }

            if (valuesDiff.getMilestone() != null) {
                if (valuesDiff.getMilestone()[1] != null) {
                    lastStatus = Status.IN_SPRINT;
                } else {
                    lastStatus = Status.BACKLOG;
                }
            }

            if (valuesDiff.getStatus() == null) {
                continue;
            }

            String statusString = valuesDiff.getStatus()[1];

            if (statusString.equalsIgnoreCase("Ready For Test")) {
                lastStatus = Status.READY_FOR_TEST;
            } else if (statusString.equalsIgnoreCase("New")) {
                lastStatus = Status.IN_SPRINT;
            } else if (statusString.equalsIgnoreCase("Done")) {
                lastStatus = Status.DONE;
            } else {
                lastStatus = Status.IN_PROGRESS;
            }
        }

        return lastStatus;
    }

    public UserStoryInterface getUserStory() {
        return userStory;
    }

    /**
     * Return the List of ItemHistory's for external processing
     *
     * @return a List of ItemHistory's
     */
    public List<ItemHistory> getHistoryList() {
        return new ArrayList<>(historyList);
    }
}
