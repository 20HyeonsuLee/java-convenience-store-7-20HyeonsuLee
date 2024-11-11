package store.exception;

import static store.exception.ExceptionMessage.PRODUCT_NOT_FOUNT_MESSAGE;

public class ProductNotFountException extends IllegalArgumentBaseException {

    public ProductNotFountException() {
        super(PRODUCT_NOT_FOUNT_MESSAGE.getMessage());
    }
}
