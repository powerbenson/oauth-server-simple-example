package tw.idb.oauth.exception;

public class OauthCodeErrorException extends RuntimeException {

    public OauthCodeErrorException() {
        super();
    }

    public OauthCodeErrorException(String message) {
        super(message);
    }
}
