package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class ProductSaveException extends RuntimeException {

    public ProductSaveException() {
    }

    public ProductSaveException(String message) {
        super(message);
    }

    public ProductSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
