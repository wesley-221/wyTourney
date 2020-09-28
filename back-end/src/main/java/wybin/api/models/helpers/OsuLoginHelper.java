package wybin.api.models.helpers;

import wybin.api.models.authentication.User;

public class OsuLoginHelper {
    private OsuOauthHelper osuOauthHelper;
    private User user;

    public OsuLoginHelper() {
    }

    public OsuLoginHelper(OsuOauthHelper osuOauthHelper, User user) {
        this.osuOauthHelper = osuOauthHelper;
        this.user = user;
    }

    public OsuOauthHelper getOsuOauthHelper() {
        return osuOauthHelper;
    }

    public void setOsuOauthHelper(OsuOauthHelper osuOauthHelper) {
        this.osuOauthHelper = osuOauthHelper;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
