package ui.services;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import settings.Settings;
import taiga.api.AuthAPI;
import taiga.model.auth.LoginResponse;
import taiga.model.auth.Tokens;
import taiga.util.AuthTokenSingleton;
import taiga.util.TokenStore;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginService extends Service<Boolean> {
    private final AuthAPI authAPI;
    private String username;
    private String password;

    public LoginService() {
        this.authAPI = new AuthAPI();
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace();
        }
    }

    public void login(String username, String password) {
        this.username = username;
        this.password = password;
        this.restart();
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                AtomicBoolean success = new AtomicBoolean(true);
                authAPI.authenticate(username, password, response -> {
                    System.out.println(response.getStatus());
                    if (response.getStatus() != 200) {
                        success.set(false);
                        return;
                    }
                    LoginResponse loginResponse = response.getContent();
                    Tokens tokens = new Tokens(loginResponse.getAuthToken(), loginResponse.getRefresh());
                    TokenStore.saveTokens(tokens);
                    AuthTokenSingleton.getInstance().setTokens(tokens);
                    Platform.runLater(() -> Settings.get().getAppModel().loadUser());
                    ;
                }).join();
                return success.get();
            }
        };
    }
}
