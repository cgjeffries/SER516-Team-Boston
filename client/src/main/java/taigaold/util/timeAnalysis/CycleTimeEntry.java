package taigaold.util.timeAnalysis;

import ui.tooltips.TooltipPoint;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CycleTimeEntry<T> extends TooltipPoint<CycleTimeEntry<T>> {
    private final T item;
    private final Date startDate;
    private final Date endDate;
    private final boolean valid;

    public CycleTimeEntry(T item, Date start, Date end, boolean valid) {
        this.item = item;
        this.startDate = start;
        this.endDate = end;
        this.valid = valid;
    }

    public CycleTimeEntry(T item, Date start, Date end) {
        this(item, start, end, true);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getTimeTaken() {
        if (startDate == null || endDate == null) {
            return 0L;
        }
        return endDate.getTime() - startDate.getTime();
    }

    public T get() {
        return item;
    }

    public Long getDaysTaken() {
        return TimeUnit.MILLISECONDS.toDays(getTimeTaken());
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
