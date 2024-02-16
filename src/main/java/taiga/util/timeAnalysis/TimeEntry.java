package taiga.util.timeAnalysis;

import java.util.Date;

public class TimeEntry {
    private final Date startDate;
    private final Date endDate;

    public TimeEntry(Date start, Date end){
        this.startDate = start;
        this.endDate = end;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getTimeTaken(){
        if(startDate == null || endDate == null){
            return 0L;
        }
        return endDate.getTime() - startDate.getTime();
    }
}
