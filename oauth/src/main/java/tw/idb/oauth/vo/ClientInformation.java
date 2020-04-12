package tw.idb.oauth.vo;

import java.util.List;

/*
 *  client資料的物件
 */

public class ClientInformation {

    private String name;
    private String clientId;
    private String clientSecret;
    private List<String> redirectUrl;
    private int accessTokenValiday;
    private int refreshTokenValiday;
    private boolean supportRefreshToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(List<String> redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getAccessTokenValiday() {
        return accessTokenValiday;
    }

    public void setAccessTokenValiday(int accessTokenValiday) {
        this.accessTokenValiday = accessTokenValiday;
    }

    public int getRefreshTokenValiday() {
        return refreshTokenValiday;
    }

    public void setRefreshTokenValiday(int refreshTokenValiday) {
        this.refreshTokenValiday = refreshTokenValiday;
    }

    public boolean isSupportRefreshToken() {
        return supportRefreshToken;
    }

    public void setSupportRefreshToken(boolean supportRefreshToken) {
        this.supportRefreshToken = supportRefreshToken;
    }
}
