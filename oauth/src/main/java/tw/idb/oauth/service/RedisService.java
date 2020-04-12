package tw.idb.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tw.idb.oauth.exception.RedisException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 *  此Service封裝了redisTemplate的指令
 *  之後可以通過注入@Service("redisService") 用裡面的方法對redis進行操作
 */

@Service("redisService")
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String ping() {
        try {
            return redisTemplate.getConnectionFactory().getConnection().ping();
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public String get(String _key) {
        try {
            return redisTemplate.opsForValue().get(_key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void set(String _key, String _value, long ttl, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(_key, _value, ttl, unit);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void set(String _key, String _value) {
        try {
            redisTemplate.opsForValue().set(_key, _value);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public String hget(String _key, String _field) {
        try {
            return (String) redisTemplate.opsForHash().get(_key, _field);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void hset(String _key, String _field, String _value) {
        try {
            redisTemplate.opsForHash().put(_key, _field, _value);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public Boolean exists(String _key) {
        try {
            return redisTemplate.hasKey(_key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }

    }

    public Map<Object, Object> hgetAll(String _key) {
        try {
            return redisTemplate.opsForHash().entries(_key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void putAll(String _key, Map<String, String> params, long ttl) {
        try {
            redisTemplate.opsForHash().putAll(_key, params);
            redisTemplate.expire(_key, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public void delete(String _key) {
        try {
            redisTemplate.delete(_key);
        } catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }
}
