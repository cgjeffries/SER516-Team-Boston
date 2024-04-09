package serviceutil;

public class Env {
    /**
     * Checks if the environment is a development environment
     * 
     * @return a boolean indicating if the application is in a development
     *         environment
     */
    public static boolean isDevEnv() {
        return System.getenv("BOSTON_ENV") == null || System.getenv("BOSTON_ENV").equals("development");
    }

    /**
     * Gets the port the router is on
     * 
     * @return The port the router is on
     */
    public static int getPort() {
        return System.getenv("PORT") == null || System.getenv("PORT").trim().equals("")
                ? 4567
                : Integer.parseInt(System.getenv("PORT"));
    }
}
