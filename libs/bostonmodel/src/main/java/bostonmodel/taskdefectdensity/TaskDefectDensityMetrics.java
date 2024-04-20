package bostonmodel.taskdefectdensity;

public class TaskDefectDensityMetrics {
    private int totalTasks;
    private int unfinisedTasks;

    public TaskDefectDensityMetrics(int totalTasks, int unfinisedTasks) {
        this.totalTasks = totalTasks;
        this.unfinisedTasks = unfinisedTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNewTasks() {
        return unfinisedTasks;
    }

}
