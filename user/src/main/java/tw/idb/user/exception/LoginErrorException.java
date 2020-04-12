package tw.idb.user.exception;

public class LoginErrorException extends RuntimeException {

    public LoginErrorException() {
        super();
    }

    public LoginErrorException(String message) {
        super(message);
    }
}
