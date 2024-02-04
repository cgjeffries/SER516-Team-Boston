package settings.appmodel;

import java.util.ArrayList;
import java.util.List;

import taiga.model.query.sprint.Project;
import taiga.model.query.sprint.Sprint;

public class AppModel {
    private List<Project> projects = new ArrayList<>();
    private List<Sprint> sprints = new ArrayList<>();

    private Project currentProject;
    private Sprint currentSprint;

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project p) {
        projects.add(p);
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
}
