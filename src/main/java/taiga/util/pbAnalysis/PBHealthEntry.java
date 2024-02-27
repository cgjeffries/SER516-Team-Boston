package taiga.util.pbAnalysis;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;
import taiga.model.query.userstories.UserStoryInterface;

public class PBHealthEntry {

    private final List<ItemHistory> historyList;
    private final UserStoryInterface userStory;

    /**
     * Enum representing the phases that a UserStory can be in
     */
    public enum Status {
        NEW,
        IN_PROGRESS,
        TESTING,
        DONE
    }

    public PBHealthEntry(List<ItemHistory> historyList, UserStoryInterface userStory) {
        this.historyList = historyList;
        Collections.sort(this.historyList);
        this.userStory = userStory;
    }

    /**
     * Computes the phase of the US on the given date from the History data of the US. 
     * @param date the date to compute the phase for
     * @return a PBHealthPhase enum describing the phase of the US at the specified date
     */
    public Status getPbStatusForDate(Date date) {
        if (date.before(userStory.getCreatedDate())) {
            return Status.NEW;
        }

        Status lastPhase = Status.NEW;

        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();

            if (history.getCreatedAt().after(date)) {
                break;
            }

            // note: check if valuesDiff has information about the phase changes?
            if (valuesDiff.getStatus() != null) {
                String statusString = valuesDiff.getStatus()[1];

                switch (statusString) {
                    case "In Progress":
                        lastPhase = Status.IN_PROGRESS;
                        break;
                    case "Testing":
                        lastPhase = Status.TESTING;
                        break;
                    case "Done":
                        lastPhase = Status.DONE;
                        break;
                    default:
                        lastPhase = Status.NEW;
                        break;
                }
            }
        }

        return lastPhase;
    }

    public UserStoryInterface getUserStory() {
        return userStory;
    }
}
