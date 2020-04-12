package tw.idb.leetcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tw.idb.leetcode.exception.BadRequestException;

/*
 *  此Service包含跟Token相關的操作
 *  之後可以通過注入@Service("tokenService") 用裡面的方法查看Token是否存在
 */

@Service("tokenService")
public class TokenService {

    private static final String ACCESS_TOKEN_KEY_PREFIX = "access:";
    private static final String ACCESS_TOKEN_TYPE = "Bearer ";

    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

    public boolean isToken(String token) {
        if (!token.contains(ACCESS_TOKEN_TYPE)) {
            throw new BadRequestException();
        }
        return redisService.exists(ACCESS_TOKEN_KEY_PREFIX + token.replace(ACCESS_TOKEN_TYPE, ""));
    }
}
