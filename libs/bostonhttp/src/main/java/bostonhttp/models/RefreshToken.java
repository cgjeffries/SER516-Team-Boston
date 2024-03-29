package bostonhttp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {

    public RefreshToken() {
    }

    public RefreshToken(String token) {
        this.refresh = token;
    }

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
