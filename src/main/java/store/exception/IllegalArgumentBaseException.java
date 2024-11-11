package store.exception;

public class IllegalArgumentBaseException extends IllegalArgumentException {

    protected IllegalArgumentBaseException(String message) {
        super(String.format("[ERROR] %s", message));
    }
}
