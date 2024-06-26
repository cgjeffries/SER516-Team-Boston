package settings.appmodel;

import bostonhttp.models.LoginResponse;
import bostonhttp.models.Tokens;
import com.google.gson.annotations.Expose;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import taiga.TaigaClient;
import taiga.models.project.Project;
import taiga.models.sprint.Sprint;
import taiga.models.user.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private static final String DEFAULT_TAIGA_API_URL = "https://api.taiga.io/api/v1/";
    @Expose
    private String apiURL;

    @Expose
    private List<Project> projects;

    @Expose
    private List<Sprint> sprints;

    @Expose
    private Tokens tokens;

    private final SimpleObjectProperty<Project> currentProject;
    private final StringProperty selectedMetric;

    private final SimpleObjectProperty<LoginResponse> user;

    public AppModel() {
        this.selectedMetric = new SimpleStringProperty();
        this.currentProject = new SimpleObjectProperty<>();
        this.user = new SimpleObjectProperty<>();
    }

    public void loadUser() {
        TaigaClient.getUsersAPI().getMe(response -> {
            if (response.getStatus() != 200) {
                return;
            }
            UserProfile profile = response.getContent();
            LoginResponse loginData = new LoginResponse();
            loginData.setGravatarId(profile.getGravatarId());
            loginData.setUsername(profile.getUsername());
            loginData.setPhoto(profile.getPhoto());
            Platform.runLater(() -> this.user.set(loginData));
        });
    }

    public String getApiURL() {
        if (apiURL == null) {
            this.apiURL = DEFAULT_TAIGA_API_URL;
        }
        return apiURL;
    }

    public void setApiURL(String url) {
        this.apiURL = url;
    }

    public List<Project> getProjects() {
        if (projects == null) {
            projects = new ArrayList<>();
        }
        return projects;
    }

    public void addProject(Project p) {
        addProject(p, false);
    }

    /**
     * Add a project to the app model
     *
     * @param project the project to add
     * @param force   Whether to force adding this project, which will remove the old instance
     */
    public void addProject(Project project, boolean force) {
        if (force) {
            projects.remove(project);
        } else if (projects.contains(project)) {
            return;
        }
        projects.add(project);
    }

    public void removeProject(Project p) {
        projects.remove(p);
    }

    public void setCurrentProject(Project p) {
        projects.stream()
                .filter(prj -> prj.equals(p))
                .findFirst()
                .ifPresent(prj -> currentProject.set(prj));
    }

    public List<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        return sprints;
    }

    public void addSprint(Sprint s) {
        sprints.add(s);
    }

    public void removeSprint(Sprint s) {
        sprints.remove(s);
    }


    public SimpleObjectProperty<Project> getCurrentProject() {
        return currentProject;
    }

    public void setSelectedMetric(String metric) {
        selectedMetric.set(metric);
    }

    public StringProperty getSelectedMetric() {
        return selectedMetric;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    public SimpleObjectProperty<LoginResponse> getUser() {
        return user;
    }

    public void setUser(LoginResponse response) {
        user.set(response);
    }
}
