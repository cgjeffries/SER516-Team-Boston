package taiga.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import taiga.api.ProjectAPI;
import taiga.model.query.project.Project;

/**
 * Class to manage projects during and inbetween runs
 * 
 */
public class MyProjectsSingleton {

    private List<String> projectSlugs;
    private Project currenProject;

    private static final MyProjectsSingleton myProjects = new MyProjectsSingleton();
    
    private MyProjectsSingleton() {
        projectSlugs = new ArrayList<String>();
        // TODO: Add functionality to load json on startup.
        //Likely should not populate with API calls, just list of strings first
    }

    public static MyProjectsSingleton getInstrance() {
        return myProjects;
    }

    public List<String> getSlugs() {
        return projectSlugs;
    }

    public boolean removeSlug(String newSlug) {
        if(projectSlugs.contains(newSlug)) {
            projectSlugs.remove(newSlug);
            return true;
        }
        return false;
    }

    public boolean addSlug(String newSlug) {
        if(!projectSlugs.contains(newSlug)) {
            projectSlugs.add(newSlug);
            return true;
        }
        return false;
    }

    public boolean setCurrentProject(String slug) {
        ProjectAPI projectAPI = new ProjectAPI();
        projectAPI.getProject(slug, result -> {currenProject = result.getContent();});
        if(currenProject.getSlug().equals(slug)) {
            addSlug(slug);
            return true;
        }
        else {
            return false;
        }
    }

    public Project getCurrentProject() {
        return currenProject;
    }

}