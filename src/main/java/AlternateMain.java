import taiga.api.ProjectAPI;
import taiga.model.query.project.ProjectListEntry;

public class AlternateMain {
    public static void main(String[] args){
        ProjectAPI projectAPI = new ProjectAPI();
        projectAPI.listProjects(
                result -> {
                    ProjectListEntry[] projects = result.getContent();
                    System.out.println("Number of projects retrieved: " + projects.length);
                    for (ProjectListEntry project: projects){
                        System.out.println(project.getName());
                    }
                })
            .join();
    }
}
