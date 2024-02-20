package taiga.util;

import settings.Settings;

public class APISettingsStore {

    private static final String DEFAULT_TAIGA_API_URL= "https://api.taiga.io/api/v1/";

    public static void saveAPIUrl(String url){
        Settings.get().getAppModel().setApiURL(url);
    }

    public static String getAPIUrl(){
        String setting = Settings.get().getAppModel().getApiURL();
        if(setting == null){
            return DEFAULT_TAIGA_API_URL;
        }
        return setting;
    }
}
