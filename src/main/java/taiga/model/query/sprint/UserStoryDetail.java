package taiga.model.query.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import taiga.model.query.userstories.UserStoryInterface;

public class UserStoryDetail implements UserStoryInterface {
    @SerializedName("assigned_to")
    @Expose
    private Integer assignedTo;

    @SerializedName("assigned_to_extra_info")
    @Expose
    private Object assignedToExtraInfo;

    @SerializedName("assigned_users")
    @Expose
    private List<Object> assignedUsers = null;

    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;

    @SerializedName("backlog_order")
    @Expose
    private long backlogOrder;

    @SerializedName("blocked_note")
    @Expose
    private String blockedNote;

    @SerializedName("client_requirement")
    @Expose
    private Boolean clientRequirement;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("created_date")
    @Expose
    private Date createdDate;

    @SerializedName("due_date")
    @Expose
    private Object dueDate;

    @SerializedName("due_date_reason")
    @Expose
    private String dueDateReason;

    @SerializedName("due_date_status")
    @Expose
    private String dueDateStatus;

    @SerializedName("epic_order")
    @Expose
    private Object epicOrder;

    @SerializedName("epics")
    @Expose
    private List<Epic> epics;

    @SerializedName("external_reference")
    @Expose
    private Object externalReference;

    @SerializedName("finish_date")
    @Expose
    private Date finishDate;

    @SerializedName("generated_from_issue")
    @Expose
    private Object generatedFromIssue;

    @SerializedName("generated_from_task")
    @Expose
    private Object generatedFromTask;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    @SerializedName("is_voter")
    @Expose
    private Boolean isVoter;

    @SerializedName("is_watcher")
    @Expose
    private Boolean isWatcher;

    @SerializedName("kanban_order")
    @Expose
    private long kanbanOrder;

    @SerializedName("milestone")
    @Expose
    private Integer milestone;

    @SerializedName("milestone_name")
    @Expose
    private Object milestoneName;

    @SerializedName("milestone_slug")
    @Expose
    private Object milestoneSlug;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("origin_issue")
    @Expose
    private Object originIssue;

    @SerializedName("origin_task")
    @Expose
    private Object originTask;

    @SerializedName("owner")
    @Expose
    private Integer owner;

    @SerializedName("owner_extra_info")
    @Expose
    private Object ownerExtraInfo;

    @SerializedName("points")
    @Expose
    private Object points;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("project_extra_info")
    @Expose
    private Object projectExtraInfo;

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
    private Object statusExtraInfo;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("tags")
    @Expose
    private List<List<String>> tags = null;

    @SerializedName("tasks")
    @Expose
    private List<Object> tasks = null;

    @SerializedName("team_requirement")
    @Expose
    private Boolean teamRequirement;

    @SerializedName("total_attachments")
    @Expose
    private Integer totalAttachments;

    @SerializedName("total_comments")
    @Expose
    private Integer totalComments;

    @SerializedName("total_points")
    @Expose
    private Double totalPoints;

    @SerializedName("total_voters")
    @Expose
    private Integer totalVoters;

    @SerializedName("total_watchers")
    @Expose
    private Integer totalWatchers;

    @SerializedName("tribe_gig")
    @Expose
    private Object tribeGig;

    @SerializedName("version")
    @Expose
    private Integer version;

    @SerializedName("watchers")
    @Expose
    private List<Object> watchers = null;

    public Integer getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Object getAssignedToExtraInfo() {
        return assignedToExtraInfo;
    }

    public void setAssignedToExtraInfo(Object assignedToExtraInfo) {
        this.assignedToExtraInfo = assignedToExtraInfo;
    }

    public List<Object> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<Object> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public long getBacklogOrder() {
        return backlogOrder;
    }

    public void setBacklogOrder(long backlogOrder) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Object getEpicOrder() {
        return epicOrder;
    }

    public void setEpicOrder(Object epicOrder) {
        this.epicOrder = epicOrder;
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

    public Object getGeneratedFromIssue() {
        return generatedFromIssue;
    }

    public void setGeneratedFromIssue(Object generatedFromIssue) {
        this.generatedFromIssue = generatedFromIssue;
    }

    public Object getGeneratedFromTask() {
        return generatedFromTask;
    }

    public void setGeneratedFromTask(Object generatedFromTask) {
        this.generatedFromTask = generatedFromTask;
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

    public Boolean getIsVoter() {
        return isVoter;
    }

    public void setIsVoter(Boolean isVoter) {
        this.isVoter = isVoter;
    }

    public Boolean getIsWatcher() {
        return isWatcher;
    }

    public void setIsWatcher(Boolean isWatcher) {
        this.isWatcher = isWatcher;
    }

    public long getKanbanOrder() {
        return kanbanOrder;
    }

    public void setKanbanOrder(long kanbanOrder) {
        this.kanbanOrder = kanbanOrder;
    }

    public Integer getMilestone() {
        return milestone;
    }

    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
    }

    public Object getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(Object milestoneName) {
        this.milestoneName = milestoneName;
    }

    public Object getMilestoneSlug() {
        return milestoneSlug;
    }

    public void setMilestoneSlug(Object milestoneSlug) {
        this.milestoneSlug = milestoneSlug;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Object getOriginIssue() {
        return originIssue;
    }

    public void setOriginIssue(Object originIssue) {
        this.originIssue = originIssue;
    }

    public Object getOriginTask() {
        return originTask;
    }

    public void setOriginTask(Object originTask) {
        this.originTask = originTask;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Object getOwnerExtraInfo() {
        return ownerExtraInfo;
    }

    public void setOwnerExtraInfo(Object ownerExtraInfo) {
        this.ownerExtraInfo = ownerExtraInfo;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Object getProjectExtraInfo() {
        return projectExtraInfo;
    }

    public void setProjectExtraInfo(Object projectExtraInfo) {
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

    public Object getStatusExtraInfo() {
        return statusExtraInfo;
    }

    public void setStatusExtraInfo(Object statusExtraInfo) {
        this.statusExtraInfo = statusExtraInfo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<List<String>> getTags() {
        return tags;
    }

    public void setTags(List<List<String>> tags) {
        this.tags = tags;
    }

    public List<Object> getTasks() {
        return tasks;
    }

    public void setTasks(List<Object> tasks) {
        this.tasks = tasks;
    }

    public Boolean getTeamRequirement() {
        return teamRequirement;
    }

    public void setTeamRequirement(Boolean teamRequirement) {
        this.teamRequirement = teamRequirement;
    }

    public Integer getTotalAttachments() {
        return totalAttachments;
    }

    public void setTotalAttachments(Integer totalAttachments) {
        this.totalAttachments = totalAttachments;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getTotalVoters() {
        return totalVoters;
    }

    public void setTotalVoters(Integer totalVoters) {
        this.totalVoters = totalVoters;
    }

    public Integer getTotalWatchers() {
        return totalWatchers;
    }

    public void setTotalWatchers(Integer totalWatchers) {
        this.totalWatchers = totalWatchers;
    }

    public Object getTribeGig() {
        return tribeGig;
    }

    public void setTribeGig(Object tribeGig) {
        this.tribeGig = tribeGig;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Object> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<Object> watchers) {
        this.watchers = watchers;
    }
}
