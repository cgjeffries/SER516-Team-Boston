package taiga.model.query.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectMemberEntry {

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("full_name_display")
    @Expose
    private String fullNameDisplay;

    @SerializedName("gravatar_id")
    @Expose
    private String gravatarId;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("role")
    @Expose
    private Integer role;

    @SerializedName("role_name")
    @Expose
    private String roleName;

    @SerializedName("username")
    @Expose
    private String username;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameDisplay() {
        return fullNameDisplay;
    }

    public void setFullNameDisplay(String fullNameDisplay) {
        this.fullNameDisplay = fullNameDisplay;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
