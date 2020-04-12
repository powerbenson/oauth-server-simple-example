package tw.idb.oauth.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tw.idb.oauth.vo.ClientInformation;

import java.util.List;

/*
 *  啟動時 Spring boot 看到 @ConfigurationProperties 會根據 prefix 去掃描 application.yaml檔案，將資料load近來
 *  資料會注入到 @Component("oauthClientConfiguration") 這個Bean裡
 *  之後能使用 Dependency Injection 的方式拿到這個物件
 */

@ConfigurationProperties(prefix = "oauthclients")
@Component("oauthClientConfiguration")
@Configuration
public class OauthClientConfiguration {

    private List<ClientInformation> clientInformations;

    public OauthClientConfiguration(List<ClientInformation> clientInformations) {
        this.clientInformations = clientInformations;
    }

    public List<ClientInformation> getClientInformations() {
        return clientInformations;
    }

    public void setClientInformations(List<ClientInformation> clientInformations) {
        this.clientInformations = clientInformations;
    }
}
