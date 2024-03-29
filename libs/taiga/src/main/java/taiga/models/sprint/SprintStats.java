package taiga.models.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SprintStats {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("estimated_start")
    @Expose
    private Date estimatedStart;

    @SerializedName("estimated_finish")
    @Expose
    private Date estimatedFinish;

    @SerializedName("total_points")
    @Expose
    private Map<String, Double> totalPoints;

    @SerializedName("completed_points")
    @Expose
    private List<Double> completedPoints;

    @SerializedName("total_userstories")
    @Expose
    private Integer totalUserstories;

    @SerializedName("completed_userstories")
    @Expose
    private Integer completedUserstories;

    @SerializedName("total_tasks")
    @Expose
    private Integer totalTasks;

    @SerializedName("completed_tasks")
    @Expose
    private Integer completedTasks;

    @SerializedName("iocaine_doses")
    @Expose
    private Integer iocaineDoses;

    @SerializedName("days")
    @Expose
    private Days[] days;

    public String getName() {
        return name;
    }

    public Date getEstimatedStart() {
        return estimatedStart;
    }

    public Date getEstimatedFinish() {
        return estimatedFinish;
    }

    public Map<String, Double> getTotalPoints() {
        return totalPoints;
    }

    public List<Double> getCompletedPoints() {
        return completedPoints;
    }

    public Integer getTotalUserstories() {
        return totalUserstories;
    }

    public Integer getCompletedUserstories() {
        return completedUserstories;
    }

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public Integer getCompletedTasks() {
        return completedTasks;
    }

    public Integer getIocaineDoses() {
        return iocaineDoses;
    }

    public Days[] getDays() {
        return days;
    }
}
