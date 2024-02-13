package taiga.model.query.customattributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStoryCustomAttribute {

    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("extra")
    @Expose
    private Object extra;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("project")
    @Expose
    private Integer project;
    @SerializedName("type")
    @Expose
    private String type;

    public String getCreatedDate() {
    return createdDate;
    }

    public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    public Object getExtra() {
    return extra;
    }

    public void setExtra(Object extra) {
    this.extra = extra;
    }

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    public String getModifiedDate() {
    return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
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

    public Integer getProject() {
    return project;
    }

    public void setProject(Integer project) {
    this.project = project;
    }

    public String getType() {
    return type;
    }

    public void setType(String type) {
    this.type = type;
    }

}