package taiga.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import taiga.model.query.tasks.Task;
public class TaskAPITest {

    @Test
    public void testlistClosedTasksByProject() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int projectId = 123;

        when(tasksAPI.listClosedTasksByProject(projectId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listClosedTasksByProject(projectId, callback);

        verify(tasksAPI).listClosedTasksByProject(projectId, callback);
    }

    @Test
    public void testlistTasksByProject() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int projectId = 123;

        boolean testbool = false;

        when(tasksAPI.listTasksByProject(projectId, testbool, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listTasksByProject(projectId, testbool, callback);

        verify(tasksAPI).listTasksByProject(projectId, testbool, callback);
    }

    @Test
    public void testlistClosedTasksByMilestone() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int milestoneID = 123;

        when(tasksAPI.listClosedTasksByMilestone(milestoneID, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listClosedTasksByMilestone(milestoneID, callback);

        verify(tasksAPI).listClosedTasksByMilestone(milestoneID, callback);
    }

    @Test
    public void testlistTasksByMilestone() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int milestoneID = 123;

        boolean testbool = false;

        when(tasksAPI.listTasksByMilestone(milestoneID, testbool, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listTasksByMilestone(milestoneID, testbool, callback);

        verify(tasksAPI).listTasksByMilestone(milestoneID, testbool, callback);
    }

    @Test
    public void testlistClosedTasksByUserStory() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int userStoryID = 123;

        when(tasksAPI.listClosedTasksByUserStory(userStoryID, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listClosedTasksByUserStory(userStoryID, callback);

        verify(tasksAPI).listClosedTasksByUserStory(userStoryID, callback);
    }

    @Test
    public void testlistTasksByUserStory() {
        TasksAPI tasksAPI = mock(TasksAPI.class);

        Consumer<APIResponse<Task[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent()); 
        };

        int userStoryID = 123;

        boolean testbool = false;

        when(tasksAPI.listTasksByUserStory(userStoryID, testbool, callback)).thenReturn(CompletableFuture.completedFuture(null));

        tasksAPI.listTasksByUserStory(userStoryID, testbool, callback);

        verify(tasksAPI).listTasksByUserStory(userStoryID, testbool, callback);
    }

}
