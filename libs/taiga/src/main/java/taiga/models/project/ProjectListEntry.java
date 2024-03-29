package taiga.models.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ProjectListEntry {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    @SerializedName("members")
    @Expose
    private List<Integer> members = null;

    @SerializedName("total_milestones")
    @Expose
    private Object totalMilestones;

    @SerializedName("total_story_points")
    @Expose
    private Object totalStoryPoints;

    @SerializedName("is_contact_activated")
    @Expose
    private Boolean isContactActivated;

    @SerializedName("is_epics_activated")
    @Expose
    private Boolean isEpicsActivated;

    @SerializedName("is_backlog_activated")
    @Expose
    private Boolean isBacklogActivated;

    @SerializedName("is_kanban_activated")
    @Expose
    private Boolean isKanbanActivated;

    @SerializedName("is_wiki_activated")
    @Expose
    private Boolean isWikiActivated;

    @SerializedName("is_issues_activated")
    @Expose
    private Boolean isIssuesActivated;

    @SerializedName("videoconferences")
    @Expose
    private Object videoconferences;

    @SerializedName("videoconferences_extra_data")
    @Expose
    private Object videoconferencesExtraData;

    @SerializedName("creation_template")
    @Expose
    private Integer creationTemplate;

    @SerializedName("is_private")
    @Expose
    private Boolean isPrivate;

    @SerializedName("anon_permissions")
    @Expose
    private List<String> anonPermissions = null;

    @SerializedName("public_permissions")
    @Expose
    private List<String> publicPermissions = null;

    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;

    @SerializedName("is_looking_for_people")
    @Expose
    private Boolean isLookingForPeople;

    @SerializedName("looking_for_people_note")
    @Expose
    private String lookingForPeopleNote;

    @SerializedName("blocked_code")
    @Expose
    private Object blockedCode;

    @SerializedName("totals_updated_datetime")
    @Expose
    private String totalsUpdatedDatetime;

    @SerializedName("total_fans")
    @Expose
    private Integer totalFans;

    @SerializedName("total_fans_last_week")
    @Expose
    private Integer totalFansLastWeek;

    @SerializedName("total_fans_last_month")
    @Expose
    private Integer totalFansLastMonth;

    @SerializedName("total_fans_last_year")
    @Expose
    private Integer totalFansLastYear;

    @SerializedName("total_activity")
    @Expose
    private Integer totalActivity;

    @SerializedName("total_activity_last_week")
    @Expose
    private Integer totalActivityLastWeek;

    @SerializedName("total_activity_last_month")
    @Expose
    private Integer totalActivityLastMonth;

    @SerializedName("total_activity_last_year")
    @Expose
    private Integer totalActivityLastYear;

    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;

    @SerializedName("tags_colors")
    @Expose
    private Map tagsColors;

    @SerializedName("default_epic_status")
    @Expose
    private Integer defaultEpicStatus;

    @SerializedName("default_points")
    @Expose
    private Integer defaultPoints;

    @SerializedName("default_us_status")
    @Expose
    private Integer defaultUsStatus;

    @SerializedName("default_task_status")
    @Expose
    private Integer defaultTaskStatus;

    @SerializedName("default_priority")
    @Expose
    private Integer defaultPriority;

    @SerializedName("default_severity")
    @Expose
    private Integer defaultSeverity;

    @SerializedName("default_issue_status")
    @Expose
    private Integer defaultIssueStatus;

    @SerializedName("default_issue_type")
    @Expose
    private Integer defaultIssueType;

    @SerializedName("default_swimlane")
    @Expose
    private Object defaultSwimlane;

    @SerializedName("my_permissions")
    @Expose
    private List<String> myPermissions = null;

    @SerializedName("i_am_owner")
    @Expose
    private Boolean iAmOwner;

    @SerializedName("i_am_admin")
    @Expose
    private Boolean iAmAdmin;

    @SerializedName("i_am_member")
    @Expose
    private Boolean iAmMember;

    @SerializedName("notify_level")
    @Expose
    private Object notifyLevel;

    @SerializedName("total_closed_milestones")
    @Expose
    private Integer totalClosedMilestones;

    @SerializedName("is_watcher")
    @Expose
    private Boolean isWatcher;

    @SerializedName("total_watchers")
    @Expose
    private Integer totalWatchers;

    @SerializedName("logo_small_url")
    @Expose
    private Object logoSmallUrl;

    @SerializedName("logo_big_url")
    @Expose
    private Object logoBigUrl;

    @SerializedName("is_fan")
    @Expose
    private Boolean isFan;

    @SerializedName("my_homepage")
    @Expose
    private String myHomepage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
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

    public Boolean getIsBacklogActivated() {
        return isBacklogActivated;
    }

    public void setIsBacklogActivated(Boolean isBacklogActivated) {
        this.isBacklogActivated = isBacklogActivated;
    }

    public Boolean getIsKanbanActivated() {
        return isKanbanActivated;
    }

    public void setIsKanbanActivated(Boolean isKanbanActivated) {
        this.isKanbanActivated = isKanbanActivated;
    }

    public Boolean getIsWikiActivated() {
        return isWikiActivated;
    }

    public void setIsWikiActivated(Boolean isWikiActivated) {
        this.isWikiActivated = isWikiActivated;
    }

    public Boolean getIsIssuesActivated() {
        return isIssuesActivated;
    }

    public void setIsIssuesActivated(Boolean isIssuesActivated) {
        this.isIssuesActivated = isIssuesActivated;
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

    public Integer getCreationTemplate() {
        return creationTemplate;
    }

    public void setCreationTemplate(Integer creationTemplate) {
        this.creationTemplate = creationTemplate;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<String> getAnonPermissions() {
        return anonPermissions;
    }

    public void setAnonPermissions(List<String> anonPermissions) {
        this.anonPermissions = anonPermissions;
    }

    public List<String> getPublicPermissions() {
        return publicPermissions;
    }

    public void setPublicPermissions(List<String> publicPermissions) {
        this.publicPermissions = publicPermissions;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsLookingForPeople() {
        return isLookingForPeople;
    }

    public void setIsLookingForPeople(Boolean isLookingForPeople) {
        this.isLookingForPeople = isLookingForPeople;
    }

    public String getLookingForPeopleNote() {
        return lookingForPeopleNote;
    }

    public void setLookingForPeopleNote(String lookingForPeopleNote) {
        this.lookingForPeopleNote = lookingForPeopleNote;
    }

    public Object getBlockedCode() {
        return blockedCode;
    }

    public void setBlockedCode(Object blockedCode) {
        this.blockedCode = blockedCode;
    }

    public String getTotalsUpdatedDatetime() {
        return totalsUpdatedDatetime;
    }

    public void setTotalsUpdatedDatetime(String totalsUpdatedDatetime) {
        this.totalsUpdatedDatetime = totalsUpdatedDatetime;
    }

    public Integer getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(Integer totalFans) {
        this.totalFans = totalFans;
    }

    public Integer getTotalFansLastWeek() {
        return totalFansLastWeek;
    }

    public void setTotalFansLastWeek(Integer totalFansLastWeek) {
        this.totalFansLastWeek = totalFansLastWeek;
    }

    public Integer getTotalFansLastMonth() {
        return totalFansLastMonth;
    }

    public void setTotalFansLastMonth(Integer totalFansLastMonth) {
        this.totalFansLastMonth = totalFansLastMonth;
    }

    public Integer getTotalFansLastYear() {
        return totalFansLastYear;
    }

    public void setTotalFansLastYear(Integer totalFansLastYear) {
        this.totalFansLastYear = totalFansLastYear;
    }

    public Integer getTotalActivity() {
        return totalActivity;
    }

    public void setTotalActivity(Integer totalActivity) {
        this.totalActivity = totalActivity;
    }

    public Integer getTotalActivityLastWeek() {
        return totalActivityLastWeek;
    }

    public void setTotalActivityLastWeek(Integer totalActivityLastWeek) {
        this.totalActivityLastWeek = totalActivityLastWeek;
    }

    public Integer getTotalActivityLastMonth() {
        return totalActivityLastMonth;
    }

    public void setTotalActivityLastMonth(Integer totalActivityLastMonth) {
        this.totalActivityLastMonth = totalActivityLastMonth;
    }

    public Integer getTotalActivityLastYear() {
        return totalActivityLastYear;
    }

    public void setTotalActivityLastYear(Integer totalActivityLastYear) {
        this.totalActivityLastYear = totalActivityLastYear;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public Map getTagsColors() {
        return tagsColors;
    }

    public void setTagsColors(Map tagsColors) {
        this.tagsColors = tagsColors;
    }

    public Integer getDefaultEpicStatus() {
        return defaultEpicStatus;
    }

    public void setDefaultEpicStatus(Integer defaultEpicStatus) {
        this.defaultEpicStatus = defaultEpicStatus;
    }

    public Integer getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(Integer defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    public Integer getDefaultUsStatus() {
        return defaultUsStatus;
    }

    public void setDefaultUsStatus(Integer defaultUsStatus) {
        this.defaultUsStatus = defaultUsStatus;
    }

    public Integer getDefaultTaskStatus() {
        return defaultTaskStatus;
    }

    public void setDefaultTaskStatus(Integer defaultTaskStatus) {
        this.defaultTaskStatus = defaultTaskStatus;
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

    public Object getDefaultSwimlane() {
        return defaultSwimlane;
    }

    public void setDefaultSwimlane(Object defaultSwimlane) {
        this.defaultSwimlane = defaultSwimlane;
    }

    public List<String> getMyPermissions() {
        return myPermissions;
    }

    public void setMyPermissions(List<String> myPermissions) {
        this.myPermissions = myPermissions;
    }

    public Boolean getiAmOwner() {
        return iAmOwner;
    }

    public void setiAmOwner(Boolean iAmOwner) {
        this.iAmOwner = iAmOwner;
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

    public Object getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(Object notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Integer getTotalClosedMilestones() {
        return totalClosedMilestones;
    }

    public void setTotalClosedMilestones(Integer totalClosedMilestones) {
        this.totalClosedMilestones = totalClosedMilestones;
    }

    public Boolean getIsWatcher() {
        return isWatcher;
    }

    public void setIsWatcher(Boolean isWatcher) {
        this.isWatcher = isWatcher;
    }

    public Integer getTotalWatchers() {
        return totalWatchers;
    }

    public void setTotalWatchers(Integer totalWatchers) {
        this.totalWatchers = totalWatchers;
    }

    public Object getLogoSmallUrl() {
        return logoSmallUrl;
    }

    public void setLogoSmallUrl(Object logoSmallUrl) {
        this.logoSmallUrl = logoSmallUrl;
    }

    public Object getLogoBigUrl() {
        return logoBigUrl;
    }

    public void setLogoBigUrl(Object logoBigUrl) {
        this.logoBigUrl = logoBigUrl;
    }

    public Boolean getIsFan() {
        return isFan;
    }

    public void setIsFan(Boolean isFan) {
        this.isFan = isFan;
    }

    public String getMyHomepage() {
        return myHomepage;
    }

    public void setMyHomepage(String myHomepage) {
        this.myHomepage = myHomepage;
    }
}
