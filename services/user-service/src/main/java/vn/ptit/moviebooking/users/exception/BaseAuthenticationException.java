package vn.ptit.moviebooking.users.exception;

public class BaseAuthenticationException extends BaseException {

    public BaseAuthenticationException(String message) {
        super("", message, null);
    }

    protected BaseAuthenticationException(String entityName, String message, Throwable error) {
        super(entityName, message, error);
    }
}
