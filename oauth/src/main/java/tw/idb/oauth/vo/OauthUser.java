package tw.idb.oauth.vo;

/*
 *  Oauth使用者資料的物件
 */

public class OauthUser {

    private User user;
    private String clientId;

    public OauthUser(User user, String clientId) {
        this.user = user;
        this.clientId = clientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
