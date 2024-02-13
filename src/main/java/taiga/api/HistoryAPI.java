package taiga.api;

import taiga.model.query.history.History;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class HistoryAPI extends APIWrapperBase {

    public HistoryAPI() {
        super("history");
    }

    public CompletableFuture<Void> getUserStoryHistory(int id, Consumer<APIResponse<History[]>> callback) {
        return queryAsync("/userstory/" + id, History[].class).thenAccept(callback);
    }
}
