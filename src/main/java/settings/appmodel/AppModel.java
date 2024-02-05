package settings.appmodel;

import com.google.gson.annotations.Expose;
import taiga.model.query.project.Project;

import java.util.ArrayList;
import java.util.List;

import taiga.model.query.sprint.Sprint;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppModel {
    @Expose
    private List<Project> projects;

    @Expose
    private List<Sprint> sprints;

    private Project currentProject;
    private Sprint currentSprint;
    private String selectedMetric;

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
                .ifPresent(prj -> currentProject = prj);
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void addSprint(Sprint s) {
        sprints.add(s);
    }

    public void removeSprint(Sprint s) {
        sprints.remove(s);
    }

    public void setCurrentSprint(Sprint s) {
        sprints.stream()
                .filter(spr -> spr.equals(s))
                .findFirst()
                .ifPresent(spr -> currentSprint = spr);
    }

    public Sprint getCurrentSprint() {
        return currentSprint;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setSelectedMetric(String metric) {
        selectedMetric = metric;
    }

    public String getSelectedMetric() {
        return selectedMetric;
    }
}
