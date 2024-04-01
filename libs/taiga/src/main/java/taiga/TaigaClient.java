package taiga;

import bostonhttp.api.APIWrapperBehaviors;
import bostonhttp.api.AuthAPI;
import taiga.api.*;

public class TaigaClient {
    private static final AuthAPI authAPI;
    private static final EpicsAPI epicsAPI;
    private static final HistoryAPI historyAPI;
    private static final ProjectAPI projectAPI;
    private static final SprintAPI sprintAPI;
    private static final SprintStatsAPI sprintStatsAPI;
    private static final TaskHistoryAPI taskHistoryAPI;
    private static final TasksAPI tasksAPI;
    private static final UsersAPI usersAPI;
    private static final UserStoryAPI userStoryAPI;
    private static final UserStoryCustomAttributesAPI userStoryCustomAttributesAPI;
    private static final UserStoryCustomAttributesValuesAPI userStoryCustomAttributesValuesAPI;
    private static final UserStoryHistoryAPI userStoryHistoryAPI;
    private static APIWrapperBehaviors behaviors;

    static {
        authAPI = new AuthAPI();
        epicsAPI = new EpicsAPI();
        historyAPI = new HistoryAPI();
        projectAPI = new ProjectAPI();
        sprintAPI = new SprintAPI();
        sprintStatsAPI = new SprintStatsAPI();
        taskHistoryAPI = new TaskHistoryAPI();
        tasksAPI = new TasksAPI();
        usersAPI = new UsersAPI();
        userStoryAPI = new UserStoryAPI();
        userStoryCustomAttributesAPI = new UserStoryCustomAttributesAPI();
        userStoryCustomAttributesValuesAPI = new UserStoryCustomAttributesValuesAPI();
        userStoryHistoryAPI = new UserStoryHistoryAPI();
    }

    public static void setDefaultBehavior(APIWrapperBehaviors behaviors) {
        TaigaClient.behaviors = behaviors;
    }

    public static void setDefaultAPIBehaviors(APIWrapperBehaviors behaviors) {
        authAPI.setBehaviors(behaviors);
        epicsAPI.setBehaviors(behaviors);
        historyAPI.setBehaviors(behaviors);
        projectAPI.setBehaviors(behaviors);
        sprintAPI.setBehaviors(behaviors);
        sprintStatsAPI.setBehaviors(behaviors);
        taskHistoryAPI.setBehaviors(behaviors);
        tasksAPI.setBehaviors(behaviors);
        usersAPI.setBehaviors(behaviors);
        userStoryAPI.setBehaviors(behaviors);
        userStoryCustomAttributesAPI.setBehaviors(behaviors);
        userStoryCustomAttributesValuesAPI.setBehaviors(behaviors);
        userStoryHistoryAPI.setBehaviors(behaviors);
    }

    public static AuthAPI getAuthAPI() {
        return authAPI;
    }

    public static EpicsAPI getEpicsAPI() {
        return epicsAPI;
    }

    public static HistoryAPI getHistoryAPI() {
        return historyAPI;
    }

    public static ProjectAPI getProjectAPI() {
        return projectAPI;
    }

    public static SprintAPI getSprintAPI() {
        return sprintAPI;
    }

    public static SprintStatsAPI getSprintStatsAPI() {
        return sprintStatsAPI;
    }

    public static TaskHistoryAPI getTaskHistoryAPI() {
        return taskHistoryAPI;
    }

    public static TasksAPI getTasksAPI() {
        return tasksAPI;
    }

    public static UsersAPI getUsersAPI() {
        return usersAPI;
    }

    public static UserStoryAPI getUserStoryAPI() {
        return userStoryAPI;
    }

    public static UserStoryCustomAttributesAPI getUserStoryCustomAttributesAPI() {
        return userStoryCustomAttributesAPI;
    }

    public static UserStoryCustomAttributesValuesAPI getUserStoryCustomAttributesValuesAPI() {
        return userStoryCustomAttributesValuesAPI;
    }

    public static UserStoryHistoryAPI getUserStoryHistoryAPI() {
        return userStoryHistoryAPI;
    }
}
