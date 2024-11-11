package store.exception;

import static store.exception.ExceptionMessage.INPUT_FORMAT_MESSAGE;

public class InputFormatException extends IllegalArgumentBaseException {

    public InputFormatException() {
        super(INPUT_FORMAT_MESSAGE.getMessage());
    }
}
