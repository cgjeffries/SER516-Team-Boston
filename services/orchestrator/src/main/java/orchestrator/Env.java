package orchestrator;

public class Env {
    /**
     * Checks if the environment is a development environment
     * @return a boolean indicating if the application is in a development environment
     */
    public static boolean isDevEnv() {
        return System.getenv("BOSTON_ENV") == null || System.getenv("BOSTON_ENV").equals("development");
    }
}
