package bostonhttp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {

    public AuthToken() {
    }

    public AuthToken(String authToken) {
        this.auth = authToken;
    }

    @SerializedName("auth")
    @Expose
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
