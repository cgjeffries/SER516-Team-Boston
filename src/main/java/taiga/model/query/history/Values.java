
package taiga.model.query.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Values {

    @SerializedName("users")
    @Expose
    private Users users;
    @SerializedName("status")
    @Expose
    private HashMap<String, String> status;
    @SerializedName("roles")
    @Expose
    private Roles roles;
    @SerializedName("points")
    @Expose
    private HashMap<String, String> points;
    @SerializedName("milestone")
    @Expose
    private HashMap<String, String> milestone;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public HashMap<String, String> getStatus() {
        return status;
    }

    public void setStatus(HashMap<String, String> status) {
        this.status = status;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public HashMap<String, String> getPoints() {
        return points;
    }

    public void setPoints(HashMap<String, String> points) {
        this.points = points;
    }

    public HashMap<String, String> getMilestone() {
        return milestone;
    }

    public void setMilestone(HashMap<String, String> milestone) {
        this.milestone = milestone;
    }

}
