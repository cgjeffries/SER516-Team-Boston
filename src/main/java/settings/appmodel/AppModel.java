package settings.appmodel;

import com.google.gson.annotations.Expose;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import taiga.model.query.project.Project;

import java.util.ArrayList;
import java.util.List;

import taiga.model.query.sprint.Sprint;

public class AppModel {
    @Expose
    private String apiURL;

    @Expose
    private List<Project> projects;

    @Expose
    private List<Sprint> sprints;

    private final SimpleObjectProperty<Project> currentProject;
    private final StringProperty selectedMetric;

    public AppModel() {
        this.selectedMetric = new SimpleStringProperty();
        this.currentProject = new SimpleObjectProperty<>();
    }

    public String getApiURL(){
        return apiURL;
    }

    public void setApiURL(String url){
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
}
