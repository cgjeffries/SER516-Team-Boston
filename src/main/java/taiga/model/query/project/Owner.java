package taiga.model.query.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;

public class Owner {

    @SerializedName("big_photo")
    @Expose
    private URL bigPhoto;

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
    private URL photo;

    @SerializedName("username")
    @Expose
    private String username;

    public URL getBigPhoto() {
        return bigPhoto;
    }

    public void setBigPhoto(URL bigPhoto) {
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

    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
