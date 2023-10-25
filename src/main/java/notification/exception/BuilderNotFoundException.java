package notification.exception;

public class BuilderNotFoundException extends RuntimeException {
    public BuilderNotFoundException(String message) {
        super(message);
    }
}