package taiga.util.timeAnalysis;

import java.util.Date;

public class CycleTimeEntry {
    private final Date startDate;
    private final Date endDate;
    private final boolean valid;

    public CycleTimeEntry(Date start, Date end, boolean valid){
        this.startDate = start;
        this.endDate = end;
        this.valid = valid;
    }

    public CycleTimeEntry(Date start, Date end) {
        this(start, end, true);
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

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "CycleTimeEntry{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
