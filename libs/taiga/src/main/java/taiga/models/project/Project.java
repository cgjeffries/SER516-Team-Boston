package taiga.models.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import taiga.TaigaClient;
import taiga.api.SprintAPI;
import taiga.models.sprint.Sprint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Project {

    @SerializedName("anon_permissions")
    @Expose
    private List<String> anonPermissions = null;

    @SerializedName("blocked_code")
    @Expose
    private Object blockedCode;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("creation_template")
    @Expose
    private Integer creationTemplate;

    @SerializedName("default_epic_status")
    @Expose
    private Integer defaultEpicStatus;

    @SerializedName("default_issue_status")
    @Expose
    private Integer defaultIssueStatus;

    @SerializedName("default_issue_type")
    @Expose
    private Integer defaultIssueType;

    @SerializedName("default_points")
    @Expose
    private Integer defaultPoints;

    @SerializedName("default_priority")
    @Expose
    private Integer defaultPriority;

    @SerializedName("default_severity")
    @Expose
    private Integer defaultSeverity;

    @SerializedName("default_task_status")
    @Expose
    private Integer defaultTaskStatus;

    @SerializedName("default_us_status")
    @Expose
    private Integer defaultUsStatus;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("i_am_admin")
    @Expose
    private Boolean iAmAdmin;

    @SerializedName("i_am_member")
    @Expose
    private Boolean iAmMember;

    @SerializedName("i_am_owner")
    @Expose
    private Boolean iAmOwner;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_backlog_activated")
    @Expose
    private Boolean isBacklogActivated;

    @SerializedName("is_contact_activated")
    @Expose
    private Boolean isContactActivated;

    @SerializedName("is_epics_activated")
    @Expose
    private Boolean isEpicsActivated;

    @SerializedName("is_fan")
    @Expose
    private Boolean isFan;

    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;

    @SerializedName("is_issues_activated")
    @Expose
    private Boolean isIssuesActivated;

    @SerializedName("is_kanban_activated")
    @Expose
    private Boolean isKanbanActivated;

    @SerializedName("is_looking_for_people")
    @Expose
    private Boolean isLookingForPeople;

    @SerializedName("is_private")
    @Expose
    private Boolean isPrivate;

    @SerializedName("is_watcher")
    @Expose
    private Boolean isWatcher;

    @SerializedName("is_wiki_activated")
    @Expose
    private Boolean isWikiActivated;

    @SerializedName("logo_big_url")
    @Expose
    private String logoBigUrl;

    @SerializedName("logo_small_url")
    @Expose
    private String logoSmallUrl;

    @SerializedName("looking_for_people_note")
    @Expose
    private String lookingForPeopleNote;

    @SerializedName("members")
    @Expose
    private List<ProjectMemberEntry> members = null;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("my_homepage")
    @Expose
    private Object myHomepage;

    @SerializedName("my_permissions")
    @Expose
    private List<String> myPermissions = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("notify_level")
    @Expose
    private Integer notifyLevel;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    @SerializedName("public_permissions")
    @Expose
    private List<String> publicPermissions = null;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("task_statuses")
    @Expose
    private TaskStatus[] taskStatuses = null;

    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    @SerializedName("tags_colors")
    @Expose
    private Map tagsColors;

    @SerializedName("total_activity")
    @Expose
    private Integer totalActivity;

    @SerializedName("total_activity_last_month")
    @Expose
    private Integer totalActivityLastMonth;

    @SerializedName("total_activity_last_week")
    @Expose
    private Integer totalActivityLastWeek;

    @SerializedName("total_activity_last_year")
    @Expose
    private Integer totalActivityLastYear;

    @SerializedName("total_closed_milestones")
    @Expose
    private Integer totalClosedMilestones;

    @SerializedName("total_fans")
    @Expose
    private Integer totalFans;

    @SerializedName("total_fans_last_month")
    @Expose
    private Integer totalFansLastMonth;

    @SerializedName("total_fans_last_week")
    @Expose
    private Integer totalFansLastWeek;

    @SerializedName("total_fans_last_year")
    @Expose
    private Integer totalFansLastYear;

    @SerializedName("total_milestones")
    @Expose
    private Object totalMilestones; // TODO: figure out what type this is supposed to be

    @SerializedName("total_story_points")
    @Expose
    private Object totalStoryPoints; // TODO: figure out what type this is supposed to be (integer?)

    @SerializedName("total_watchers")
    @Expose
    private Integer totalWatchers;

    @SerializedName("totals_updated_datetime")
    @Expose
    private String totalsUpdatedDatetime;

    @SerializedName("videoconferences")
    @Expose
    private Object videoconferences; // TODO: figure out what type this is supposed to be

    @SerializedName("videoconferences_extra_data")
    @Expose
    private Object videoconferencesExtraData; // TODO: figure out what type this is supposed to be

    @SerializedName("sprints")
    @Expose
    private List<Sprint> sprints;


    public CompletableFuture<Void> loadSprints() {
        return TaigaClient.getSprintAPI().listSprints(getId(), result -> {
            if (result.getStatus() != 200) {
                return;
            }
            sprints = Arrays.asList(result.getContent());
        });
    }

    public List<String> getAnonPermissions() {

        return anonPermissions;
    }

    public void setAnonPermissions(List<String> anonPermissions) {
        this.anonPermissions = anonPermissions;
    }

    public Object getBlockedCode() {
        return blockedCode;
    }

    public void setBlockedCode(Object blockedCode) {
        this.blockedCode = blockedCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreationTemplate() {
        return creationTemplate;
    }

    public void setCreationTemplate(Integer creationTemplate) {
        this.creationTemplate = creationTemplate;
    }

    public Integer getDefaultEpicStatus() {
        return defaultEpicStatus;
    }

    public void setDefaultEpicStatus(Integer defaultEpicStatus) {
        this.defaultEpicStatus = defaultEpicStatus;
    }

    public Integer getDefaultIssueStatus() {
        return defaultIssueStatus;
    }

    public void setDefaultIssueStatus(Integer defaultIssueStatus) {
        this.defaultIssueStatus = defaultIssueStatus;
    }

    public Integer getDefaultIssueType() {
        return defaultIssueType;
    }

    public void setDefaultIssueType(Integer defaultIssueType) {
        this.defaultIssueType = defaultIssueType;
    }

    public Integer getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(Integer defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    public Integer getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(Integer defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public Integer getDefaultSeverity() {
        return defaultSeverity;
    }

    public void setDefaultSeverity(Integer defaultSeverity) {
        this.defaultSeverity = defaultSeverity;
    }

    public Integer getDefaultTaskStatus() {
        return defaultTaskStatus;
    }

    public void setDefaultTaskStatus(Integer defaultTaskStatus) {
        this.defaultTaskStatus = defaultTaskStatus;
    }

    public Integer getDefaultUsStatus() {
        return defaultUsStatus;
    }

    public void setDefaultUsStatus(Integer defaultUsStatus) {
        this.defaultUsStatus = defaultUsStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getiAmAdmin() {
        return iAmAdmin;
    }

    public void setiAmAdmin(Boolean iAmAdmin) {
        this.iAmAdmin = iAmAdmin;
    }

    public Boolean getiAmMember() {
        return iAmMember;
    }

    public void setiAmMember(Boolean iAmMember) {
        this.iAmMember = iAmMember;
    }

    public Boolean getiAmOwner() {
        return iAmOwner;
    }

    public void setiAmOwner(Boolean iAmOwner) {
        this.iAmOwner = iAmOwner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsBacklogActivated() {
        return isBacklogActivated;
    }

    public void setIsBacklogActivated(Boolean isBacklogActivated) {
        this.isBacklogActivated = isBacklogActivated;
    }

    public Boolean getIsContactActivated() {
        return isContactActivated;
    }

    public void setIsContactActivated(Boolean isContactActivated) {
        this.isContactActivated = isContactActivated;
    }

    public Boolean getIsEpicsActivated() {
        return isEpicsActivated;
    }

    public void setIsEpicsActivated(Boolean isEpicsActivated) {
        this.isEpicsActivated = isEpicsActivated;
    }

    public Boolean getIsFan() {
        return isFan;
    }

    public void setIsFan(Boolean isFan) {
        this.isFan = isFan;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsIssuesActivated() {
        return isIssuesActivated;
    }

    public void setIsIssuesActivated(Boolean isIssuesActivated) {
        this.isIssuesActivated = isIssuesActivated;
    }

    public Boolean getIsKanbanActivated() {
        return isKanbanActivated;
    }

    public void setIsKanbanActivated(Boolean isKanbanActivated) {
        this.isKanbanActivated = isKanbanActivated;
    }

    public Boolean getIsLookingForPeople() {
        return isLookingForPeople;
    }

    public void setIsLookingForPeople(Boolean isLookingForPeople) {
        this.isLookingForPeople = isLookingForPeople;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getIsWatcher() {
        return isWatcher;
    }

    public void setIsWatcher(Boolean isWatcher) {
        this.isWatcher = isWatcher;
    }

    public Boolean getIsWikiActivated() {
        return isWikiActivated;
    }

    public void setIsWikiActivated(Boolean isWikiActivated) {
        this.isWikiActivated = isWikiActivated;
    }

    public String getLogoBigUrl() {
        return logoBigUrl;
    }

    public void setLogoBigUrl(String logoBigUrl) {
        this.logoBigUrl = logoBigUrl;
    }

    public String getLogoSmallUrl() {
        return logoSmallUrl;
    }

    public void setLogoSmallUrl(String logoSmallUrl) {
        this.logoSmallUrl = logoSmallUrl;
    }

    public String getLookingForPeopleNote() {
        return lookingForPeopleNote;
    }

    public void setLookingForPeopleNote(String lookingForPeopleNote) {
        this.lookingForPeopleNote = lookingForPeopleNote;
    }

    public List<ProjectMemberEntry> getMembers() {
        return members;
    }

    public void setMembers(List<ProjectMemberEntry> members) {
        this.members = members;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Object getMyHomepage() {
        return myHomepage;
    }

    public void setMyHomepage(Object myHomepage) {
        this.myHomepage = myHomepage;
    }

    public List<String> getMyPermissions() {
        return myPermissions;
    }

    public void setMyPermissions(List<String> myPermissions) {
        this.myPermissions = myPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(Integer notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<String> getPublicPermissions() {
        return publicPermissions;
    }

    public void setPublicPermissions(List<String> publicPermissions) {
        this.publicPermissions = publicPermissions;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public TaskStatus[] getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(TaskStatus[] taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map getTagsColors() {
        return tagsColors;
    }

    public void setTagsColors(Map tagsColors) {
        this.tagsColors = tagsColors;
    }

    public Integer getTotalActivity() {
        return totalActivity;
    }

    public void setTotalActivity(Integer totalActivity) {
        this.totalActivity = totalActivity;
    }

    public Integer getTotalActivityLastMonth() {
        return totalActivityLastMonth;
    }

    public void setTotalActivityLastMonth(Integer totalActivityLastMonth) {
        this.totalActivityLastMonth = totalActivityLastMonth;
    }

    public Integer getTotalActivityLastWeek() {
        return totalActivityLastWeek;
    }

    public void setTotalActivityLastWeek(Integer totalActivityLastWeek) {
        this.totalActivityLastWeek = totalActivityLastWeek;
    }

    public Integer getTotalActivityLastYear() {
        return totalActivityLastYear;
    }

    public void setTotalActivityLastYear(Integer totalActivityLastYear) {
        this.totalActivityLastYear = totalActivityLastYear;
    }

    public Integer getTotalClosedMilestones() {
        return totalClosedMilestones;
    }

    public void setTotalClosedMilestones(Integer totalClosedMilestones) {
        this.totalClosedMilestones = totalClosedMilestones;
    }

    public Integer getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(Integer totalFans) {
        this.totalFans = totalFans;
    }

    public Integer getTotalFansLastMonth() {
        return totalFansLastMonth;
    }

    public void setTotalFansLastMonth(Integer totalFansLastMonth) {
        this.totalFansLastMonth = totalFansLastMonth;
    }

    public Integer getTotalFansLastWeek() {
        return totalFansLastWeek;
    }

    public void setTotalFansLastWeek(Integer totalFansLastWeek) {
        this.totalFansLastWeek = totalFansLastWeek;
    }

    public Integer getTotalFansLastYear() {
        return totalFansLastYear;
    }

    public void setTotalFansLastYear(Integer totalFansLastYear) {
        this.totalFansLastYear = totalFansLastYear;
    }

    public Object getTotalMilestones() {
        return totalMilestones;
    }

    public void setTotalMilestones(Object totalMilestones) {
        this.totalMilestones = totalMilestones;
    }

    public Object getTotalStoryPoints() {
        return totalStoryPoints;
    }

    public void setTotalStoryPoints(Object totalStoryPoints) {
        this.totalStoryPoints = totalStoryPoints;
    }

    public Integer getTotalWatchers() {
        return totalWatchers;
    }

    public void setTotalWatchers(Integer totalWatchers) {
        this.totalWatchers = totalWatchers;
    }

    public String getTotalsUpdatedDatetime() {
        return totalsUpdatedDatetime;
    }

    public void setTotalsUpdatedDatetime(String totalsUpdatedDatetime) {
        this.totalsUpdatedDatetime = totalsUpdatedDatetime;
    }

    public Object getVideoconferences() {
        return videoconferences;
    }

    public void setVideoconferences(Object videoconferences) {
        this.videoconferences = videoconferences;
    }

    public Object getVideoconferencesExtraData() {
        return videoconferencesExtraData;
    }

    public void setVideoconferencesExtraData(Object videoconferencesExtraData) {
        this.videoconferencesExtraData = videoconferencesExtraData;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(slug, project.slug) &&
                Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, id);
    }
}
