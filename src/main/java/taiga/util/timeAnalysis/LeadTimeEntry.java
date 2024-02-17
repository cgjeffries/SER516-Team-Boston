package taiga.util.timeAnalysis;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import taiga.model.query.history.History;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;

public class LeadTimeEntry{

    private List<ItemHistory> historyList;
    private Date createdDate;

    public enum Status{
        NOT_CREATED,
        BACKLOG,
        IN_SPRINT,
        IN_PROGRESS,
        READY_FOR_TEST,
        DONE
    }


    public LeadTimeEntry(List<ItemHistory> historyList, Date createdDate){
        this.historyList=historyList;
        Collections.sort(this.historyList);
        this.createdDate = createdDate;
    }

    /**
     *
     * @param date
     * @return
     */
    public Status getStatusForDate(Date date){
        if(date.before(createdDate)){
            return Status.NOT_CREATED;
        }

        Status lastStatus = Status.BACKLOG;

        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();

            if(history.getCreatedAt().after(date)){
                break;
            }

            if(valuesDiff.getMilestone() != null){
                if(valuesDiff.getMilestone()[1] != null){
                    lastStatus = Status.IN_SPRINT;
                }
                else{
                    lastStatus = Status.BACKLOG;
                }
            }

            if(valuesDiff.getStatus() == null){
                continue;
            }

            String statusString = valuesDiff.getStatus()[1];

            if(statusString.equalsIgnoreCase("Ready For Test")){
                lastStatus = Status.READY_FOR_TEST;
            }
            else if (statusString.equalsIgnoreCase("New")){
                lastStatus = Status.IN_SPRINT;
            }
            else if (statusString.equalsIgnoreCase("Done")){
                lastStatus = Status.DONE;
            }
            else{
                lastStatus = Status.IN_PROGRESS;
            }
        }

        return lastStatus;
    }

}
