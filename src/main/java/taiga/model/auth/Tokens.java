package taiga.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tokens {
    public Tokens() {}

    public Tokens(String authToken, String refreshToken) {
        this.auth = authToken;
        this.refresh = refreshToken;
    }

    @SerializedName("refresh")
    @Expose
    private String refresh;

    @SerializedName("auth")
    @Expose
    private String auth;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
