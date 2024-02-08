package taiga.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import taiga.model.query.project.ProjectListEntry;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class ProjectAPITest {

    @Test
    void testListProjects() {
        // Mock the APIWrapperBase class
        APIWrapperBase apiWrapperMock = Mockito.mock(APIWrapperBase.class);

        // Create an instance of the ProjectAPI class using the mocked APIWrapperBase
        ProjectAPI projectAPI = new ProjectAPI();

        // Mock the APIResponse
        APIResponse<ProjectListEntry[]> mockApiResponse = new APIResponse<>(200, new ProjectListEntry[]{});

        // Use Mockito to set up the behavior of the queryAsync method in the APIWrapperBase mock
        Mockito.when(apiWrapperMock.queryAsync(Mockito.anyString(), Mockito.eq(ProjectListEntry[].class), Mockito.eq(true), Mockito.eq(true)))
                .thenReturn(CompletableFuture.completedFuture(mockApiResponse));

        // Use a CompletableFuture to wait for the asynchronous method to complete
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Define a callback function that will be executed upon receiving the query result
        Consumer<APIResponse<ProjectListEntry[]>> callback = response -> {

            future.complete(null); 
        };

        // Call the listProjects method with the mocked callback
        projectAPI.listProjects(callback);

        // Wait for the CompletableFuture
        future.join();
    }
}