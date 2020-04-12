package tw.idb.oauth.exception;

public class RedisException extends RuntimeException {

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }
}
