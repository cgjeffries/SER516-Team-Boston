package taiga.model.query.taskhistory;

import java.util.Map;

public class TaskHistoryValues {
    private Map<String, String> status;

    private Map<String, String> users;

    public Map<String, String> getStatus() {
        return status;
    }

    public Map<String, String> getUsers() {
        return users;
    }
}
