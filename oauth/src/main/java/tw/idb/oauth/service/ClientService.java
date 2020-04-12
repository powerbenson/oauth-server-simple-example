package tw.idb.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tw.idb.oauth.conf.OauthClientConfiguration;
import tw.idb.oauth.vo.ClientInformation;

import java.util.List;

/*
 *  這邊注入了 oauthClientConfiguration 拿到註冊過的 Oauth Client 資料
 *  之後可以通過注入@Service("clientService") 用裡面的方法拿到 Oauth Client 資料
 */

@Service("clientService")
public class ClientService {

    @Autowired
    @Qualifier("oauthClientConfiguration")
    private OauthClientConfiguration oauthClientConfiguration;

    public List<ClientInformation> getClients() {
        return oauthClientConfiguration.getClientInformations();
    }

    public boolean compareClientIdAndRedirectUrl(String clientId, String redirectUrl) {
        return this.getClients()
                .stream()
                .anyMatch(clients -> clients.getClientId().equals(clientId) && clients.getRedirectUrl().stream().anyMatch(url -> url.equals(redirectUrl)));
    }

    public boolean compareCliendIdAndClientSecretAndRedirectUrl(String clientId, String clientSecret, String redirectUrl) {
        return this.getClients()
                .stream()
                .anyMatch(clients -> clients.getClientId().equals(clientId) && clients.getClientSecret().equals(clientSecret) && clients.getRedirectUrl().stream().anyMatch(url -> url.equals(redirectUrl)));
    }

    public int getAccessTokenValidayByClientId(String clientId) {
        return this.getClients()
                .stream()
                .filter(clients -> clients.getClientId().equals(clientId))
                .map(ClientInformation::getAccessTokenValiday)
                .findFirst()
                .orElse(0);
    }

    public int getRefreshTokenValidayByClientId(String clientId) {
        return this.getClients()
                .stream()
                .filter(clients -> clients.getClientId().equals(clientId))
                .map(ClientInformation::getRefreshTokenValiday)
                .findFirst()
                .orElse(0);
    }

    public boolean isSupportRefreshToken(String clientId) {
        return this.getClients()
                .stream()
                .anyMatch(clients -> clients.equals(clientId) && clients.isSupportRefreshToken());
    }
}
