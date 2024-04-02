package ui.metrics.burndown;

import java.util.Date;

public class MultiBurnDownEntry {
    private final Date date;
    private final DataSet taskData;
    private final DataSet userStoryData;
    private final DataSet businessValueData;

    public MultiBurnDownEntry(Date date, DataSet taskData, DataSet userStoryData, DataSet businessValueData) {
        this.date = date;
        this.taskData = taskData;
        this.userStoryData = userStoryData;
        this.businessValueData = businessValueData;
    }

    // getters
    public Date getDate() {
        return date;
    }

    public DataSet getTaskData() {
        return taskData;
    }

    public DataSet getUserStoryData() {
        return userStoryData;
    }

    public DataSet getBusinessValueData() {
        return businessValueData;
    }

    @Override
    public String toString() {
        return "MultiBurnDownEntry{" +
                "date=" + date +
                ", taskData=" + taskData +
                ", userStoryData=" + userStoryData +
                ", businessValueData=" + businessValueData +
                '}';
    }

    // nested DataSet class for burndownentries
    public static class DataSet {
        private final double ideal;
        private final double current;

        public DataSet(double ideal, double current) {
            this.ideal = ideal;
            this.current = current;
        }

        // getters
        public double getIdeal() {
            return ideal;
        }

        public double getCurrent() {
            return current;
        }

        @Override
        public String toString() {
            return "DataSet{" +
                    "ideal=" + ideal +
                    ", current=" + current +
                    '}';
        }
    }
}
