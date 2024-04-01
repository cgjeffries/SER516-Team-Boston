package taiga.models.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import taiga.models.common.AssignedToExtraInfo;
import taiga.models.common.Epic;
import taiga.models.common.StatusExtraInfo;
import taiga.models.userstories.UserStoryInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserStory implements UserStoryInterface {

    @SerializedName("assigned_to")
    @Expose
    private Integer assignedTo;

    @SerializedName("assigned_to_extra_info")
    @Expose
    private AssignedToExtraInfo assignedToExtraInfo;

    @SerializedName("backlog_order")
    @Expose
    private Long backlogOrder;

    @SerializedName("blocked_note")
    @Expose
    private String blockedNote;

    @SerializedName("client_requirement")
    @Expose
    private Boolean clientRequirement;

    @SerializedName("created_date")
    @Expose
    private Date createdDate;

    @SerializedName("due_date")
    @Expose
    private Object dueDate; // TODO figure out what this is

    @SerializedName("due_date_reason")
    @Expose
    private String dueDateReason;

    @SerializedName("due_date_status")
    @Expose
    private String dueDateStatus;

    @SerializedName("epics")
    @Expose
    private List<Epic> epics = null;

    @SerializedName("external_reference")
    @Expose
    private Object externalReference; // TODO figure out what this is

    @SerializedName("finish_date")
    @Expose
    private Date finishDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    @SerializedName("kanban_order")
    @Expose
    private Long kanbanOrder;

    @SerializedName("milestone")
    @Expose
    private Integer milestone;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("points")
    @Expose
    private Map<String, String> points;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("project_extra_info")
    @Expose
    private UserStoryProjectExtraInfo projectExtraInfo;

    @SerializedName("ref")
    @Expose
    private Integer ref;

    @SerializedName("sprint_order")
    @Expose
    private Long sprintOrder;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("status_extra_info")
    @Expose
    private StatusExtraInfo statusExtraInfo;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("team_requirement")
    @Expose
    private Boolean teamRequirement;

    @SerializedName("total_points")
    @Expose
    private Double totalPoints;

    @SerializedName("version")
    @Expose
    private Integer version;

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public AssignedToExtraInfo getAssignedToExtraInfo() {
        return assignedToExtraInfo;
    }

    public void setAssignedToExtraInfo(AssignedToExtraInfo assignedToExtraInfo) {
        this.assignedToExtraInfo = assignedToExtraInfo;
    }

    public Long getBacklogOrder() {
        return backlogOrder;
    }

    public void setBacklogOrder(Long backlogOrder) {
        this.backlogOrder = backlogOrder;
    }

    public String getBlockedNote() {
        return blockedNote;
    }

    public void setBlockedNote(String blockedNote) {
        this.blockedNote = blockedNote;
    }

    public Boolean getClientRequirement() {
        return clientRequirement;
    }

    public void setClientRequirement(Boolean clientRequirement) {
        this.clientRequirement = clientRequirement;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Object getDueDate() {
        return dueDate;
    }

    public void setDueDate(Object dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateReason() {
        return dueDateReason;
    }

    public void setDueDateReason(String dueDateReason) {
        this.dueDateReason = dueDateReason;
    }

    public String getDueDateStatus() {
        return dueDateStatus;
    }

    public void setDueDateStatus(String dueDateStatus) {
        this.dueDateStatus = dueDateStatus;
    }

    public List<Epic> getEpics() {
        return epics;
    }

    public void setEpics(List<Epic> epics) {
        this.epics = epics;
    }

    public Object getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(Object externalReference) {
        this.externalReference = externalReference;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Long getKanbanOrder() {
        return kanbanOrder;
    }

    public void setKanbanOrder(Long kanbanOrder) {
        this.kanbanOrder = kanbanOrder;
    }

    public Integer getMilestone() {
        return milestone;
    }

    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Map<String, String> getPoints() {
        return points;
    }

    public void setPoints(Map<String, String> points) {
        this.points = points;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public UserStoryProjectExtraInfo getProjectExtraInfo() {
        return projectExtraInfo;
    }

    public void setProjectExtraInfo(UserStoryProjectExtraInfo projectExtraInfo) {
        this.projectExtraInfo = projectExtraInfo;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Long getSprintOrder() {
        return sprintOrder;
    }

    public void setSprintOrder(Long sprintOrder) {
        this.sprintOrder = sprintOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public StatusExtraInfo getStatusExtraInfo() {
        return statusExtraInfo;
    }

    public void setStatusExtraInfo(StatusExtraInfo statusExtraInfo) {
        this.statusExtraInfo = statusExtraInfo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getTeamRequirement() {
        return teamRequirement;
    }

    public void setTeamRequirement(Boolean teamRequirement) {
        this.teamRequirement = teamRequirement;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
