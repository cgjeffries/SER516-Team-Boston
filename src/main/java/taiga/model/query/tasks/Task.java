package taiga.model.query.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import taiga.model.query.common.AssignedToExtraInfo;
import taiga.model.query.common.OwnerExtraInfo;
import taiga.model.query.common.ProjectExtraInfo;
import taiga.model.query.common.StatusExtraInfo;

public class Task {

    @SerializedName("assigned_to")
    @Expose
    private Integer assignedTo;

    @SerializedName("assigned_to_extra_info")
    @Expose
    private AssignedToExtraInfo assignedToExtraInfo;

    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null; // TODO figure out what this does

    @SerializedName("blocked_note")
    @Expose
    private String blockedNote;

    @SerializedName("created_date")
    @Expose
    private Date createdDate;

    @SerializedName("due_date")
    @Expose
    private Object dueDate; // TODO figure out what this does

    @SerializedName("due_date_reason")
    @Expose
    private String dueDateReason;

    @SerializedName("due_date_status")
    @Expose
    private String dueDateStatus;

    @SerializedName("external_reference")
    @Expose
    private Object externalReference; // TODO figure out what this does

    @SerializedName("finished_date")
    @Expose
    private Date finishedDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    @SerializedName("is_iocaine")
    @Expose
    private Boolean isIocaine;

    @SerializedName("is_voter")
    @Expose
    private Boolean isVoter;

    @SerializedName("is_watcher")
    @Expose
    private Boolean isWatcher;

    @SerializedName("milestone")
    @Expose
    private Integer milestone;

    @SerializedName("milestone_slug")
    @Expose
    private String milestoneSlug;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("owner")
    @Expose
    private Integer owner;

    @SerializedName("owner_extra_info")
    @Expose
    private OwnerExtraInfo ownerExtraInfo;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("project_extra_info")
    @Expose
    private ProjectExtraInfo projectExtraInfo;

    @SerializedName("ref")
    @Expose
    private Integer ref;

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
    private List<List<String>> tags = null;

    @SerializedName("taskboard_order")
    @Expose
    private Long taskboardOrder;

    @SerializedName("total_comments")
    @Expose
    private Integer totalComments;

    @SerializedName("total_voters")
    @Expose
    private Integer totalVoters;

    @SerializedName("total_watchers")
    @Expose
    private Integer totalWatchers;

    @SerializedName("us_order")
    @Expose
    private Long usOrder;

    @SerializedName("user_story")
    @Expose
    private Integer userStory;

    @SerializedName("user_story_extra_info")
    @Expose
    private UserStoryExtraInfo userStoryExtraInfo;

    @SerializedName("version")
    @Expose
    private Integer version;

    @SerializedName("watchers")
    @Expose
    private List<Integer> watchers = null;

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

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public String getBlockedNote() {
        return blockedNote;
    }

    public void setBlockedNote(String blockedNote) {
        this.blockedNote = blockedNote;
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

    public Object getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(Object externalReference) {
        this.externalReference = externalReference;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
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

    public Boolean getIsIocaine() {
        return isIocaine;
    }

    public void setIsIocaine(Boolean isIocaine) {
        this.isIocaine = isIocaine;
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

    public Integer getMilestone() {
        return milestone;
    }

    public void setMilestone(Integer milestone) {
        this.milestone = milestone;
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

    public Integer getProject() {
        return project;
    }

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

    public List<List<String>> getTags() {
        return tags;
    }

    public void setTags(List<List<String>> tags) {
        this.tags = tags;
    }

    public Long getTaskboardOrder() {
        return taskboardOrder;
    }

    public void setTaskboardOrder(Long taskboardOrder) {
        this.taskboardOrder = taskboardOrder;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
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

    public Long getUsOrder() {
        return usOrder;
    }

    public void setUsOrder(Long usOrder) {
        this.usOrder = usOrder;
    }

    public Integer getUserStory() {
        return userStory;
    }

    public void setUserStory(Integer userStory) {
        this.userStory = userStory;
    }

    public UserStoryExtraInfo getUserStoryExtraInfo() {
        return userStoryExtraInfo;
    }

    public void setUserStoryExtraInfo(UserStoryExtraInfo userStoryExtraInfo) {
        this.userStoryExtraInfo = userStoryExtraInfo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Integer> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<Integer> watchers) {
        this.watchers = watchers;
    }
}
