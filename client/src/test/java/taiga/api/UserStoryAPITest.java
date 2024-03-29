package taiga.api;

import bostonhttp.api.APIResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taiga.models.sprint.UserStoryDetail;
import taiga.models.userstories.UserStory;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class UserStoryAPITest {

    @Test
    public void testListUserStories() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStoryDetail[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        when(userStoryAPI.listUserStories(callback)).thenReturn(CompletableFuture.completedFuture(null));

        userStoryAPI.listUserStories(callback);

        verify(userStoryAPI).listUserStories(callback);
    }

    @Test
    public void testListProjectUserStories() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStoryDetail[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int projectId = 123;

        when(userStoryAPI.listProjectUserStories(projectId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        userStoryAPI.listProjectUserStories(projectId, callback);

        verify(userStoryAPI).listProjectUserStories(projectId, callback);
    }

    @Test
    public void testListMilestoneUserStories() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStoryDetail[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int milestoneId = 456;

        when(userStoryAPI.listMilestoneUserStories(milestoneId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        userStoryAPI.listMilestoneUserStories(milestoneId, callback);

        verify(userStoryAPI).listMilestoneUserStories(milestoneId, callback);
    }

    @Test
    public void testGetUserStory() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStoryDetail>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int userStoryId = 789;

        when(userStoryAPI.getUserStory(userStoryId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        userStoryAPI.getUserStory(userStoryId, callback);

        verify(userStoryAPI).getUserStory(userStoryId, callback);
    }

    @Test
    public void testGetUserStoryByRef() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStoryDetail>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int userStoryRef = 101;
        int projectId = 123;

        when(userStoryAPI.getUserStoryByRef(userStoryRef, projectId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        userStoryAPI.getUserStoryByRef(userStoryRef, projectId, callback);

        verify(userStoryAPI).getUserStoryByRef(userStoryRef, projectId, callback);
    }

    @Test
    public void testGetUserStoryTotalPoints() {
        UserStoryAPI userStoryAPI = mock(UserStoryAPI.class);

        Consumer<APIResponse<UserStory>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int userStoryId = 789;

        when(userStoryAPI.getUserStoryTotalPoints(userStoryId, callback))
                .thenReturn(CompletableFuture.completedFuture(10.0));

        userStoryAPI.getUserStoryTotalPoints(userStoryId, callback);

        verify(userStoryAPI).getUserStoryTotalPoints(userStoryId, callback);
    }
}
