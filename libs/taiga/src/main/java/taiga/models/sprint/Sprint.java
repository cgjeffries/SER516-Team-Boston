package taiga.models.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import taiga.models.common.ProjectExtraInfo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Sprint {

    @SerializedName("closed")
    @Expose
    private Boolean closed;

    @SerializedName("closed_points")
    @Expose
    private Double closedPoints;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("disponibility")
    @Expose
    private Double disponibility;

    @SerializedName("estimated_finish")
    @Expose
    private Date estimatedFinish;

    @SerializedName("estimated_start")
    @Expose
    private Date estimatedStart;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("modified_date")
    @Expose
    private Date modifiedDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("order")
    @Expose
    private Integer order;

    @SerializedName("owner")
    @Expose
    private Integer owner;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("project_extra_info")
    @Expose
    private ProjectExtraInfo projectExtraInfo;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("total_points")
    @Expose
    private Double totalPoints;

    @SerializedName("user_stories")
    @Expose
    private List<UserStory> userStories = null;

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Double getClosedPoints() {
        return closedPoints;
    }

    public void setClosedPoints(Double closedPoints) {
        this.closedPoints = closedPoints;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Double getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(Double disponibility) {
        this.disponibility = disponibility;
    }

    public Date getEstimatedFinish() {
        return estimatedFinish;
    }

    public void setEstimatedFinish(Date estimatedFinish) {
        this.estimatedFinish = estimatedFinish;
    }

    public Date getEstimatedStart() {
        return estimatedStart;
    }

    public void setEstimatedStart(Date estimatedStart) {
        this.estimatedStart = estimatedStart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return Objects.equals(slug, sprint.slug) &&
                Objects.equals(id, sprint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, id);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
