package taiga.models.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import taiga.models.common.AssignedToExtraInfo;
import taiga.models.common.OwnerExtraInfo;
import taiga.models.common.ProjectExtraInfo;
import taiga.models.common.StatusExtraInfo;

import java.util.Date;
import java.util.List;

public class EpicDetail {

    @SerializedName("assigned_to")
    @Expose
    private Integer assignedTo;
    @SerializedName("assigned_to_extra_info")
    @Expose
    private AssignedToExtraInfo assignedToExtraInfo;
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments;
    @SerializedName("blocked_note")
    @Expose
    private String blockedNote;
    @SerializedName("blocked_note_html")
    @Expose
    private String blockedNoteHtml;
    @SerializedName("client_requirement")
    @Expose
    private Boolean clientRequirement;
    @SerializedName("color")
    @Expose
    private String color;
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
    @SerializedName("epics_order")
    @Expose
    private Long epicsOrder;
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
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("neighbors")
    @Expose
    private Neighbors neighbors;
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
    private List<List<String>> tags;
    @SerializedName("team_requirement")
    @Expose
    private Boolean teamRequirement;
    @SerializedName("total_voters")
    @Expose
    private Integer totalVoters;
    @SerializedName("total_watchers")
    @Expose
    private Integer totalWatchers;
    @SerializedName("user_stories_counts")
    @Expose
    private UserStoriesCounts userStoriesCounts;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("watchers")
    @Expose
    private List<Integer> watchers;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Long getEpicsOrder() {
        return epicsOrder;
    }

    public void setEpicsOrder(Long epicsOrder) {
        this.epicsOrder = epicsOrder;
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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Neighbors getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Neighbors neighbors) {
        this.neighbors = neighbors;
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

    public Boolean getTeamRequirement() {
        return teamRequirement;
    }

    public void setTeamRequirement(Boolean teamRequirement) {
        this.teamRequirement = teamRequirement;
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

    public UserStoriesCounts getUserStoriesCounts() {
        return userStoriesCounts;
    }

    public void setUserStoriesCounts(UserStoriesCounts userStoriesCounts) {
        this.userStoriesCounts = userStoriesCounts;
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
