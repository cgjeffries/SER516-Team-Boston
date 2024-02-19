package taiga.model.query.sprinthistory;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SprintStats {

    @SerializedName("completed_points")
    @Expose
    private List<Object> completedPoints;

    @SerializedName("completed_tasks")
    @Expose
    private Integer completedTasks;

    @SerializedName("completed_userstories")
    @Expose
    private Integer completedUserstories;

    @SerializedName("days")
    @Expose
    private List<Day> days;

    @SerializedName("estimated_finish")
    @Expose
    private String estimatedFinish;

    @SerializedName("estimated_start")
    @Expose
    private String estimatedStart;

    @SerializedName("iocaine_doses")
    @Expose
    private Integer iocaineDoses;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_points")
    @Expose
    private HashMap<String, Double> totalPoints;

    @SerializedName("total_tasks")
    @Expose
    private Integer totalTasks;

    @SerializedName("total_userstories")
    @Expose
    private Integer totalUserstories;

    public List<Object> getCompletedPoints() {
        return completedPoints;
    }

    public void setCompletedPoints(List<Object> completedPoints) {
        this.completedPoints = completedPoints;
    }

    public Integer getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Integer completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Integer getCompletedUserstories() {
        return completedUserstories;
    }

    public void setCompletedUserstories(Integer completedUserstories) {
        this.completedUserstories = completedUserstories;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String getEstimatedFinish() {
        return estimatedFinish;
    }

    public void setEstimatedFinish(String estimatedFinish) {
        this.estimatedFinish = estimatedFinish;
    }

    public String getEstimatedStart() {
        return estimatedStart;
    }

    public void setEstimatedStart(String estimatedStart) {
        this.estimatedStart = estimatedStart;
    }

    public Integer getIocaineDoses() {
        return iocaineDoses;
    }

    public void setIocaineDoses(Integer iocaineDoses) {
        this.iocaineDoses = iocaineDoses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Double> getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(HashMap<String, Double> totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getTotalUserstories() {
        return totalUserstories;
    }

    public void setTotalUserstories(Integer totalUserstories) {
        this.totalUserstories = totalUserstories;
    }

}