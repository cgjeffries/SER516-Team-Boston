package taiga.model.query.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerExtraInfo {

    @SerializedName("big_photo")
    @Expose
    private Object bigPhoto; // TODO figure out what this does

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
    private Object photo; // TODO figure out what this does

    @SerializedName("username")
    @Expose
    private String username;

    public Object getBigPhoto() {
        return bigPhoto;
    }

    public void setBigPhoto(Object bigPhoto) {
        this.bigPhoto = bigPhoto;
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

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
