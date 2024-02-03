package settings.appmodel;

import com.google.gson.annotations.Expose;
import taiga.model.query.project.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppModel {
    @Expose
    private List<Project> projects;

    private Project currentProject;

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
        Optional<Project> project = projects.stream().filter(p::equals).findFirst();
        project.ifPresent(prj -> currentProject = prj);
    }

    public Project getCurrentProject() {
        return currentProject;
    }
}
