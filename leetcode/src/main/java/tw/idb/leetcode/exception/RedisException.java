package tw.idb.leetcode.exception;

public class RedisException extends RuntimeException {

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }
}
