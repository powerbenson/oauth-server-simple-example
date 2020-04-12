package tw.idb.oauth.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tw.idb.oauth.vo.OauthToken;
import tw.idb.oauth.vo.OauthUser;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
 *  此Service包含跟OauthToken有關的操作，如創建oauth token、refresh token、store、remove
 *  之後可以通過注入@Service("oauthTokenService") 用裡面的方法對OauthToken進行操作
 */

@Service("oauthTokenService")
public class OauthTokenService {

    private static final String ACCESS_TOKEN_KEY_PREFIX = "access:";
    private static final String OAUTHUSER_KEY_PREFIX = "oauthuser:";

    private static final String REFRESH_TO_ACCESS_KEY_PREFIX = "refresh_to_access:";
    private static final String REFRESH_OAUTHUSER_KEY_PREFIX = "refresh_oauthuser:";

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;

    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

    public OauthToken createOauhToken(OauthUser oauthUser) {
        OauthToken oauthToken = new OauthToken();
        int accessTokenValidity = clientService.getAccessTokenValidayByClientId(oauthUser.getClientId());
        int refreshTokenValidity = clientService.getAccessTokenValidayByClientId(oauthUser.getClientId());
        boolean supportRefreshToken = clientService.isSupportRefreshToken(oauthUser.getClientId());

        oauthToken.setAccess_token(createOauthToken());
        oauthToken.setExpires_in(String.valueOf(accessTokenValidity));

        if (!supportRefreshToken) {
            oauthToken.setRefresh_token(createOauthToken());
            storeRefreshToken(oauthToken, oauthUser, refreshTokenValidity);
        }

        storeAccessToken(oauthToken, oauthUser, accessTokenValidity);

        return oauthToken;
    }

    public OauthToken refreshToken(String refreshToken) {
        Gson gson = new Gson();
        String accessToken = redisService.get(REFRESH_TO_ACCESS_KEY_PREFIX + refreshToken);
        OauthUser oauthUser = gson.fromJson(redisService.get(REFRESH_OAUTHUSER_KEY_PREFIX + refreshToken), OauthUser.class);
        removeAccessToken(accessToken);
        removeRefreshToken(refreshToken);

        return createOauhToken(oauthUser);
    }

    private void storeAccessToken(OauthToken oauthToken, OauthUser oauthUser, int accessTokenValidity) {
        Gson gson = new Gson();
        redisService.set(ACCESS_TOKEN_KEY_PREFIX + oauthToken.getAccess_token(), gson.toJson(oauthToken), accessTokenValidity, TimeUnit.SECONDS);
        redisService.set(OAUTHUSER_KEY_PREFIX + oauthToken.getAccess_token(), gson.toJson(oauthUser), accessTokenValidity, TimeUnit.SECONDS);
    }

    private void storeRefreshToken(OauthToken oauthToken, OauthUser oauthUser, int refreshTokenValidity) {
        Gson gson = new Gson();
        redisService.set(REFRESH_TO_ACCESS_KEY_PREFIX + oauthToken.getRefresh_token(), oauthToken.getAccess_token(), refreshTokenValidity, TimeUnit.SECONDS);
        redisService.set(REFRESH_OAUTHUSER_KEY_PREFIX + oauthToken.getRefresh_token(), gson.toJson(oauthUser), refreshTokenValidity, TimeUnit.SECONDS);
    }

    private void removeAccessToken(String accessToken) {
        redisService.delete(ACCESS_TOKEN_KEY_PREFIX + accessToken);
        redisService.delete(OAUTHUSER_KEY_PREFIX + accessToken);
    }

    private void removeRefreshToken(String refreshToken) {
        redisService.delete(REFRESH_TO_ACCESS_KEY_PREFIX + refreshToken);
        redisService.delete(REFRESH_OAUTHUSER_KEY_PREFIX + refreshToken);
    }

    private String createOauthToken() {
        return UUID.randomUUID().toString();
    }

}
