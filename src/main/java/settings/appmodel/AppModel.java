package settings.appmodel;

import java.util.List;
import java.util.Optional;

public class AppModel {
    private List<Project> projects;
    private Project currentProject;

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project p) {
        this.projects.add(p);
    }

    public void removeProject(Project p) {
        this.projects.remove(p);
    }

    public void setCurrentProject(Project p) {
        Optional<Project> project = projects.stream().findFirst().filter(p::equals);
        project.ifPresent(prj -> currentProject = prj);
    }
}
