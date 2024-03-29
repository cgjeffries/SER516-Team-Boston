package taiga.api;

import bostonhttp.api.APIResponse;
import bostonhttp.api.APIWrapperBase;
import taiga.models.epic.EpicDetail;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EpicsAPI extends APIWrapperBase {
    public EpicsAPI() {
        super("epics");
    }


    /**
     * Lists all Epics user is authorized to view asynchronously.
     *
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listEpics(
            Consumer<APIResponse<EpicDetail[]>> callback) {
        return queryAsync("", EpicDetail[].class).thenAccept(callback);
    }

    /**
     * Lists the Epics for the given project asynchronously.
     *
     * @param projectId project id
     * @param callback  Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> listProjectEpics(
            int projectId, Consumer<APIResponse<EpicDetail[]>> callback) {
        return queryAsync("?project=" + projectId, EpicDetail[].class).thenAccept(callback);
    }

    /**
     * Gets the Epic with the given ID asynchronously.
     *
     * @param epicId   epic id
     * @param callback Consumer function to execute upon receiving query result.
     * @return void future which can be joined to wait for call to complete.
     */
    public CompletableFuture<Void> getEpic(
            int epicId, Consumer<APIResponse<EpicDetail>> callback) {
        return queryAsync("/" + epicId, EpicDetail.class).thenAccept(callback);
    }
}
