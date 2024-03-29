package taiga.models.userstories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import taiga.models.common.*;

import java.util.Date;
import java.util.List;

public class UserStory implements UserStoryInterface {

    @SerializedName("assigned_to")
    @Expose
    private Integer assignedTo;

    @SerializedName("assigned_to_extra_info")
    @Expose
    private AssignedToExtraInfo assignedToExtraInfo;

    @SerializedName("assigned_users")
    @Expose
    private List<Integer> assignedUsers;

    @SerializedName("attachments")
    @Expose
    private List<Object> attachments;

    @SerializedName("backlog_order")
    @Expose
    private Long backlogOrder;

    @SerializedName("blocked_note")
    @Expose
    private String blockedNote;

    @SerializedName("blocked_note_html")
    @Expose
    private String blockedNoteHtml;

    @SerializedName("client_requirement")
    @Expose
    private Boolean clientRequirement;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("created_date")
    @Expose
    private Date createdDate;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("description_html")
    @Expose
    private String descriptionHtml;

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
    private Long kanbanOrder;

    @SerializedName("milestone")
    @Expose
    private Integer milestone;

    @SerializedName("milestone_name")
    @Expose
    private String milestoneName;

    @SerializedName("milestone_slug")
    @Expose
    private String milestoneSlug;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    // @SerializedName("neighbors")
    // @Expose
    // private Neighbors neighbors;

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
    private OwnerExtraInfo ownerExtraInfo;

    // @SerializedName("points")
    // @Expose
    // private Points points;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("project_extra_info")
    @Expose
    private ProjectExtraInfo projectExtraInfo;

    @SerializedName("ref")
    @Expose
    private Integer ref;

    @SerializedName("sprint_order")
    @Expose
    private Integer sprintOrder;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("status_extra_info")
    @Expose
    private StatusExtraInfo statusExtraInfo;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("tags")
    @Expose
    private List<List<String>> tags;

    @SerializedName("tasks")
    @Expose
    private List<Object> tasks;

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
    private List<Object> watchers;

    @Override
    public Integer getAssignedTo() {
        return assignedTo;
    }

    @Override
    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public AssignedToExtraInfo getAssignedToExtraInfo() {
        return assignedToExtraInfo;
    }

    public void setAssignedToExtraInfo(AssignedToExtraInfo assignedToExtraInfo) {
        this.assignedToExtraInfo = assignedToExtraInfo;
    }

    public List<Integer> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<Integer> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
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

    public String getBlockedNoteHtml() {
        return blockedNoteHtml;
    }

    public void setBlockedNoteHtml(String blockedNoteHtml) {
        this.blockedNoteHtml = blockedNoteHtml;
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

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    @Override
    public Object getDueDate() {
        return dueDate;
    }

    @Override
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

    @Override
    public List<Epic> getEpics() {
        return epics;
    }

    @Override
    public void setEpics(List<Epic> epics) {
        this.epics = epics;
    }

    public Object getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(Object externalReference) {
        this.externalReference = externalReference;
    }

    @Override
    public Date getFinishDate() {
        return finishDate;
    }

    @Override
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public Long getKanbanOrder() {
        return kanbanOrder;
    }

    public void setKanbanOrder(Long kanbanOrder) {
        this.kanbanOrder = kanbanOrder;
    }

    @Override
    public Integer getMilestone() {
        return milestone;
    }

    @Override
    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneSlug() {
        return milestoneSlug;
    }

    public void setMilestoneSlug(String milestoneSlug) {
        this.milestoneSlug = milestoneSlug;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    // public Neighbors getNeighbors() {
    //     return neighbors;
    // }

    // public void setNeighbors(Neighbors neighbors) {
    //     this.neighbors = neighbors;
    // }

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

    public OwnerExtraInfo getOwnerExtraInfo() {
        return ownerExtraInfo;
    }

    public void setOwnerExtraInfo(OwnerExtraInfo ownerExtraInfo) {
        this.ownerExtraInfo = ownerExtraInfo;
    }

    // public Points getPoints() {
    //     return points;
    // }

    // public void setPoints(Points points) {
    //     this.points = points;
    // }

    @Override
    public Integer getProject() {
        return project;
    }

    @Override
    public void setProject(Integer project) {
        this.project = project;
    }

    public ProjectExtraInfo getProjectExtraInfo() {
        return projectExtraInfo;
    }

    public void setProjectExtraInfo(ProjectExtraInfo projectExtraInfo) {
        this.projectExtraInfo = projectExtraInfo;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getSprintOrder() {
        return sprintOrder;
    }

    public void setSprintOrder(Integer sprintOrder) {
        this.sprintOrder = sprintOrder;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
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

    @Override
    public Double getTotalPoints() {
        return totalPoints;
    }

    @Override
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
