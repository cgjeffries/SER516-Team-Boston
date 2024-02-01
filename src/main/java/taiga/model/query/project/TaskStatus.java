package taiga.model.query.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskStatus {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("order")
    @Expose
    private Integer order;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("project_id")
    @Expose
    private Integer projectId;

    @SerializedName("slug")
    @Expose
    private String slug;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "TaskStatus{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", order="
                + order
                + ", isClosed="
                + isClosed
                + ", color='"
                + color
                + '\''
                + ", projectId="
                + projectId
                + ", slug='"
                + slug
                + '\''
                + '}';
    }
}
