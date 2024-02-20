package taiga.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import taiga.model.query.sprint.UserStoryDetail;

public class EpicsAPI extends APIWrapperBase {
    public EpicsAPI(){
        super("epics");
    }

//    public CompletableFuture<Void> listEpics(
//        Consumer<APIResponse<EpicDetail[]>> callback) {
//        return queryAsync("", EpicDetail[].class).thenAccept(callback);
//    }
}
