package tw.idb.oauth.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;
import tw.idb.oauth.vo.OauthUser;

import java.util.concurrent.TimeUnit;

/*
 *  創建、消費、儲存、刪除 code的Service
 *  之後可以通過注入@Service("authorizeCodeService") 用裡面的方法進行上述操作
 */

@Service("authorizeCodeService")
public class AuthorizeCodeService {

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(32);

    private static final int CODE_TTL = 5;

    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

    public String createAuthorizationCodeAndStore(OauthUser oauthUser) {
        String code = createAuthorizationCode(oauthUser);
        store(code, new Gson().toJson(oauthUser));

        return code;
    }

    public String createAuthorizationCode(OauthUser oauthUser) {
        String code = generator.generate();
        return code;
    }

    public OauthUser consumeAuthorizationCode(String code) {
        String oauthUser = redisService.get(code);

        if (oauthUser != null)
            remove(code);

        return new Gson().fromJson(oauthUser, OauthUser.class);
    }

    private void store(String code, String oauthUser) {
        redisService.set(code, oauthUser, CODE_TTL, TimeUnit.MINUTES);
    }

    private void remove(String code) {
        redisService.delete(code);
    }
}
