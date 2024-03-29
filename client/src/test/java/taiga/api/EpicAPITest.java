package taiga.api;

import bostonhttp.api.APIResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taiga.models.epic.EpicDetail;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;


public class EpicAPITest {


    @Test
    public void testListEpics() {
        EpicsAPI epicsAPI = mock(EpicsAPI.class);

        Consumer<APIResponse<EpicDetail[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        when(epicsAPI.listEpics(callback)).thenReturn(CompletableFuture.completedFuture(null));

        epicsAPI.listEpics(callback);

        verify(epicsAPI).listEpics(callback);

    }

    @Test
    public void testListProjectEpics() {
        EpicsAPI epicsAPI = mock(EpicsAPI.class);

        Consumer<APIResponse<EpicDetail[]>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int projectId = 123;

        when(epicsAPI.listProjectEpics(projectId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        epicsAPI.listProjectEpics(projectId, callback);

        verify(epicsAPI).listProjectEpics(projectId, callback);
    }

    @Test
    public void testGetEpic() {
        EpicsAPI epicsAPI = mock(EpicsAPI.class);

        Consumer<APIResponse<EpicDetail>> callback = response -> {
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertNull(response.getContent());
        };

        int epicId = 789;

        when(epicsAPI.getEpic(epicId, callback)).thenReturn(CompletableFuture.completedFuture(null));

        epicsAPI.getEpic(epicId, callback);

        verify(epicsAPI).getEpic(epicId, callback);
    }


}
