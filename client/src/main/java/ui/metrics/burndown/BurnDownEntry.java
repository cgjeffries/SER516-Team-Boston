package ui.metrics.burndown;

import java.util.Date;

public class BurnDownEntry {
    private final double ideal;
    private final double current;
    private final Date date;

    public BurnDownEntry(double ideal, double current, Date date) {
        this.ideal = ideal;
        this.current = current;
        this.date = date;
    }

    public double getIdeal() {
        return ideal;
    }

    public double getCurrent() {
        return current;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "BurnDownEntry{" +
                "ideal=" + ideal +
                ", current=" + current +
                ", date=" + date +
                '}';
    }
}
