package store.exception;

import static store.exception.ExceptionMessage.ORDER_FORMAT_MESSAGE;

public class OrderFormatException extends IllegalArgumentBaseException {

    public OrderFormatException() {
        super(ORDER_FORMAT_MESSAGE.getMessage());
    }
}
