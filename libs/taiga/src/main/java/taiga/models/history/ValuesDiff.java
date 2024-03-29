package taiga.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValuesDiff {

    @SerializedName("status")
    @Expose
    private List<String> status;
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
    private CustomAttributes customAttributes;
    @SerializedName("points")
    @Expose
    private Object points;
    @SerializedName("subject")
    @Expose
    private List<String> subject;
    @SerializedName("milestone")
    @Expose
    private List<Object> milestone;
    @SerializedName("sprint_order")
    @Expose
    private List<Long> sprintOrder;

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
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

    public CustomAttributes getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(CustomAttributes customAttributes) {
        this.customAttributes = customAttributes;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public List<Object> getMilestone() {
        return milestone;
    }

    public void setMilestone(List<Object> milestone) {
        this.milestone = milestone;
    }

    public List<Long> getSprintOrder() {
        return sprintOrder;
    }

    public void setSprintOrder(List<Long> sprintOrder) {
        this.sprintOrder = sprintOrder;
    }

}
