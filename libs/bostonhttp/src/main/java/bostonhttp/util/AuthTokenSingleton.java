package bostonhttp.util;

import bostonhttp.models.Tokens;

public class AuthTokenSingleton {
    private static AuthTokenSingleton instance;

    private Tokens tokens;

    private AuthTokenSingleton() {
        tokens = TokenStore.retrieveTokens();
    }

    /**
     * method to get the auth tokens stored in the singleton
     *
     * @return Tokens object containing the auth and refresh tokens
     */
    public Tokens getTokens() {
        return tokens;
    }

    /**
     * Sets the tokens store din the singleton to the given tokens object. Also saves the tokens to
     * disk.
     *
     * @param tokens the tokens object contianing the new auth and refresh tokens to be used
     */
    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
        TokenStore.saveTokens(tokens);
    }

    /**
     * Get the instance of the util.AuthTokenSingleton if it exists, otherwise create one.
     *
     * @return the util.AuthTokenSingleton object
     */
    public static AuthTokenSingleton getInstance() {
        if (instance == null) {
            instance = new AuthTokenSingleton();
        }
        return instance;
    }
}
