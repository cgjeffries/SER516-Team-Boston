package bostonhttp.util;

import bostonhttp.models.Tokens;

public class TokenStore {
    private static TokenSaver tokenSaver;
    private static TokenRetriever tokenRetriever;

    public static void setTokenSaver(TokenSaver saver) {
        TokenStore.tokenSaver = saver;
    }

    public static void setTokenRetriever(TokenRetriever tokenRetriever) {
        TokenStore.tokenRetriever = tokenRetriever;
    }

    /**
     * Saves the Tokens object to disk using DataStore
     *
     * @param tokens the Tokens object that is wished to be saved to disk
     */
    public static void saveTokens(Tokens tokens) {
        try {
            if (TokenStore.tokenSaver != null) {
                TokenStore.tokenSaver.save(tokens);
            }
        } catch (Exception e) {
            System.out.println(
                    "Unable to save auth tokens!"); // should we pop up a warning message?
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the Tokens object that was previously saved to disk.
     *
     * @return The saved Tokens object if the read was successful, null if it wasn't.
     */
    public static Tokens retrieveTokens() {
        try {
            if (TokenStore.tokenRetriever != null) {
                return TokenStore.tokenRetriever.retrieve();
            }
            return null;
        } catch (Exception e) {
            System.out.println(
                    "Unable to retrieve auth tokens!"); // do we need to print the stack trace?
            e.printStackTrace();
            return null;
        }
    }

    public interface TokenSaver {
        void save(Tokens tokens);
    }

    public interface TokenRetriever {
        Tokens retrieve();
    }
}
