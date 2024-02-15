package taiga.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import taiga.model.query.sprint.Sprint;


class SprintAPITest {

    @Test
    void testListSprints() {
        // Mock the base APIWrapperBase class which SprintAPI extends
        APIWrapperBase apiWrapperMock = Mockito.mock(APIWrapperBase.class);

        // Create an instance of the SprintAPI class using the mocked APIWrapperBase
        SprintAPI sprintAPI = new SprintAPI();

        // Prepare the mock response
        Sprint[] mockSprints = new Sprint[]{}; // Initialize with test data if necessary
        APIResponse<Sprint[]> mockApiResponse = new APIResponse<>(200, mockSprints);

        // Use Mockito to set up the behavior of the queryAsync method in the APIWrapperBase mock
        Mockito.when(apiWrapperMock.queryAsync(Mockito.anyString(), Mockito.eq(Sprint[].class)))
                .thenReturn(CompletableFuture.completedFuture(mockApiResponse));

        // Use a CompletableFuture to wait for the asynchronous method to complete
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Define a callback function that will be executed upon receiving the query result
        Consumer<APIResponse<Sprint[]>> callback = response -> {
            // We can add further assertions to verify the response if needed
            future.complete(null); 
        };

        // Call the listSprints method with the mocked callback
        sprintAPI.listSprints(1, callback); // Assuming 1 is a test project ID

        // Wait for the CompletableFuture to ensure the asynchronous operation completes
        future.join();
    }
}
