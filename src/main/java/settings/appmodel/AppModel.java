package settings.appmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppModel {
    private List<Project> projects;
    private Project currentProject;

    public AppModel() {
        projects = new ArrayList<Project>();
    }

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
        for (Project project: projects) {
            if(project.equals(p)) {
                currentProject = p;
            }
        }
    }

    public Project getCurrentProject() {
        return currentProject;
    }
}
