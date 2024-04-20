package bostonmodel.taskexcess;

public class TaskExcessMetrics {
    private int totalTasks;
    private int newTasks;
    private double taskExcessRatio;

    public TaskExcessMetrics(int totalTasks, int newTasks, double taskExcessRatio) {
        this.totalTasks = totalTasks;
        this.newTasks = newTasks;
        this.taskExcessRatio = taskExcessRatio;
    }

    public int getTotalTasks() {
        return totalTasks;
    }
    public int getNewTasks() {
        return newTasks;
    }
    public double getTaskExcessRatio() {
        return taskExcessRatio;
    }

}
