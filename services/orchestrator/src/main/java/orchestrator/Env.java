package orchestrator;

public class Env {
    public static boolean isDevEnv() {
        return System.getenv("ORCHESTRATOR_ENV") == null || System.getenv("ORCHESTRATOR_ENV").equals("development");
    }
}
