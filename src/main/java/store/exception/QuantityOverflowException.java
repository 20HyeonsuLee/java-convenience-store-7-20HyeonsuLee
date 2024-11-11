package store.exception;

import static store.exception.ExceptionMessage.QUANTITY_OVERFLOW_MESSAGE;

public class QuantityOverflowException extends IllegalArgumentBaseException {

    public QuantityOverflowException() {
        super(QUANTITY_OVERFLOW_MESSAGE.getMessage());
    }
}
