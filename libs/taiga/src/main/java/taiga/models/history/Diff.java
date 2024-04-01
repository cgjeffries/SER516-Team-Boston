package taiga.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Diff {

    @SerializedName("status")
    @Expose
    private List<Integer> status;
    @SerializedName("kanban_order")
    @Expose
    private List<Long> kanbanOrder;
    @SerializedName("is_closed")
    @Expose
    private List<Boolean> isClosed;
    @SerializedName("finish_date")
    @Expose
    private List<String> finishDate;
    @SerializedName("custom_attributes")
    @Expose
    private List<List<Object>> customAttributes;
    @SerializedName("points")
    @Expose
    private List<HashMap<String, Long>> points;
    @SerializedName("subject")
    @Expose
    private List<String> subject;
    @SerializedName("milestone")
    @Expose
    private List<Long> milestone;
    @SerializedName("sprint_order")
    @Expose
    private List<Long> sprintOrder;

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Long> getKanbanOrder() {
        return kanbanOrder;
    }

    public void setKanbanOrder(List<Long> kanbanOrder) {
        this.kanbanOrder = kanbanOrder;
    }

    public List<Boolean> getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(List<Boolean> isClosed) {
        this.isClosed = isClosed;
    }

    public List<String> getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(List<String> finishDate) {
        this.finishDate = finishDate;
    }

    public List<List<Object>> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<List<Object>> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public List<HashMap<String, Long>> getPoints() {
        return points;
    }

    public void setPoints(List<HashMap<String, Long>> points) {
        this.points = points;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public List<Long> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<Long> milestone) {
        this.milestone = milestone;
    }

    public List<Long> getSprintOrder() {
        return sprintOrder;
    }

    public void setSprintOrder(List<Long> sprintOrder) {
        this.sprintOrder = sprintOrder;
    }

}
