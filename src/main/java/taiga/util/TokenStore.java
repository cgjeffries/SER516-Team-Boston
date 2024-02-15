package taiga.util;

import taiga.model.auth.Tokens;

public class TokenStore {
    /**
     * Saves the Tokens object to disk using DataStore
     *
     * @param tokens the Tokens object that is wished to be saved to disk
     */
    public static void saveTokens(Tokens tokens) {
        try {
            //TODO: Build out a way of storing the auth tokens

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
            //TODO: Build out a way of storing the auth tokens
            return null;
        } catch (Exception e) {
            System.out.println(
                    "Unable to retrieve auth tokens!"); // do we need to print the stack trace?
            e.printStackTrace();
            return null;
        }
    }
}
