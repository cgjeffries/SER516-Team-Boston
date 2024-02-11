
package taiga.model.query.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("diff")
    @Expose
    private Diff diff;
    @SerializedName("snapshot")
    @Expose
    private Object snapshot;
    @SerializedName("values")
    @Expose
    private Values values;
    @SerializedName("values_diff")
    @Expose
    private ValuesDiff valuesDiff;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("comment_html")
    @Expose
    private String commentHtml;
    @SerializedName("delete_comment_date")
    @Expose
    private Object deleteCommentDate;
    @SerializedName("delete_comment_user")
    @Expose
    private Object deleteCommentUser;
    @SerializedName("edit_comment_date")
    @Expose
    private Object editCommentDate;
    @SerializedName("is_hidden")
    @Expose
    private Boolean isHidden;
    @SerializedName("is_snapshot")
    @Expose
    private Boolean isSnapshot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Diff getDiff() {
        return diff;
    }

    public void setDiff(Diff diff) {
        this.diff = diff;
    }

    public Object getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Object snapshot) {
        this.snapshot = snapshot;
    }

    public Values getValues() {
        return values;
    }

    public void setValues(Values values) {
        this.values = values;
    }

    public ValuesDiff getValuesDiff() {
        return valuesDiff;
    }

    public void setValuesDiff(ValuesDiff valuesDiff) {
        this.valuesDiff = valuesDiff;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentHtml() {
        return commentHtml;
    }

    public void setCommentHtml(String commentHtml) {
        this.commentHtml = commentHtml;
    }

    public Object getDeleteCommentDate() {
        return deleteCommentDate;
    }

    public void setDeleteCommentDate(Object deleteCommentDate) {
        this.deleteCommentDate = deleteCommentDate;
    }

    public Object getDeleteCommentUser() {
        return deleteCommentUser;
    }

    public void setDeleteCommentUser(Object deleteCommentUser) {
        this.deleteCommentUser = deleteCommentUser;
    }

    public Object getEditCommentDate() {
        return editCommentDate;
    }

    public void setEditCommentDate(Object editCommentDate) {
        this.editCommentDate = editCommentDate;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getIsSnapshot() {
        return isSnapshot;
    }

    public void setIsSnapshot(Boolean isSnapshot) {
        this.isSnapshot = isSnapshot;
    }

}
