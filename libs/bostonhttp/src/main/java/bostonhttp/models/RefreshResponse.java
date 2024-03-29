package bostonhttp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshResponse {

    @SerializedName("auth_token")
    @Expose
    private String authToken;

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
