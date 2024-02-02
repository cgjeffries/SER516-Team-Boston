package taiga.util;

import java.util.ArrayList;
import java.util.List;

import taiga.model.query.project.Project;

/**
 * Class to manage projects during and inbetween runs
 * 
 */
public class MyProjectsSingleton {

    private List<String> projectStubs;
    private Project currenProject;

    private static final MyProjectsSingleton myProjects = new MyProjectsSingleton();

    
    private MyProjectsSingleton() {
        projectStubs = new ArrayList<String>();
        // TODO: Add functionality to load json on startup.
        //Likely should not populate with API calls, just list of strings first
    }

    public static MyProjectsSingleton getInstrance() {
        return myProjects;
    }

    public List<String> getStubs() {
        return projectStubs;
    }

    public boolean removeStub(String newStub) {
        if(projectStubs.contains(newStub)) {
            projectStubs.remove(newStub);
            return true;
        }
        return false;
    }

    public boolean addStub(String newStub) {
        if(!projectStubs.contains(newStub)) {
            projectStubs.add(newStub);
            return true;
        }
        return false;
    }

}