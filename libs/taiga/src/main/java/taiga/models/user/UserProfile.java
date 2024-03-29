package taiga.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfile {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("full_name_display")
    @Expose
    private String fullNameDisplay;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("theme")
    @Expose
    private String theme;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("big_photo")
    @Expose
    private String bigPhoto;

    @SerializedName("gravatar_id")
    @Expose
    private String gravatarId;

    @SerializedName("roles")
    @Expose
    private List<String> roles = null;

    @SerializedName("total_private_projects")
    @Expose
    private Integer totalPrivateProjects;

    @SerializedName("total_public_projects")
    @Expose
    private Integer totalPublicProjects;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("date_joined")
    @Expose
    private String dateJoined;

    @SerializedName("read_new_terms")
    @Expose
    private Boolean readNewTerms;

    @SerializedName("accepted_terms")
    @Expose
    private Boolean acceptedTerms;

    @SerializedName("max_private_projects")
    @Expose
    private Integer maxPrivateProjects;

    @SerializedName("max_public_projects")
    @Expose
    private Integer maxPublicProjects;

    @SerializedName("max_memberships_private_projects")
    @Expose
    private Integer maxMembershipsPrivateProjects;

    @SerializedName("max_memberships_public_projects")
    @Expose
    private Integer maxMembershipsPublicProjects;

    @SerializedName("verified_email")
    @Expose
    private Boolean verifiedEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    public String getBigPhoto() {
        return bigPhoto;
    }

    public void setBigPhoto(String bigPhoto) {
        this.bigPhoto = bigPhoto;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Integer getTotalPrivateProjects() {
        return totalPrivateProjects;
    }

    public void setTotalPrivateProjects(Integer totalPrivateProjects) {
        this.totalPrivateProjects = totalPrivateProjects;
    }

    public Integer getTotalPublicProjects() {
        return totalPublicProjects;
    }

    public void setTotalPublicProjects(Integer totalPublicProjects) {
        this.totalPublicProjects = totalPublicProjects;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Boolean getReadNewTerms() {
        return readNewTerms;
    }

    public void setReadNewTerms(Boolean readNewTerms) {
        this.readNewTerms = readNewTerms;
    }

    public Boolean getAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(Boolean acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public Integer getMaxPrivateProjects() {
        return maxPrivateProjects;
    }

    public void setMaxPrivateProjects(Integer maxPrivateProjects) {
        this.maxPrivateProjects = maxPrivateProjects;
    }

    public Integer getMaxPublicProjects() {
        return maxPublicProjects;
    }

    public void setMaxPublicProjects(Integer maxPublicProjects) {
        this.maxPublicProjects = maxPublicProjects;
    }

    public Integer getMaxMembershipsPrivateProjects() {
        return maxMembershipsPrivateProjects;
    }

    public void setMaxMembershipsPrivateProjects(Integer maxMembershipsPrivateProjects) {
        this.maxMembershipsPrivateProjects = maxMembershipsPrivateProjects;
    }

    public Integer getMaxMembershipsPublicProjects() {
        return maxMembershipsPublicProjects;
    }

    public void setMaxMembershipsPublicProjects(Integer maxMembershipsPublicProjects) {
        this.maxMembershipsPublicProjects = maxMembershipsPublicProjects;
    }

    public Boolean getVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(Boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }
}