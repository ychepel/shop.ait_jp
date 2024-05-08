package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class ProductUpdateException extends RuntimeException {

    public ProductUpdateException() {
    }

    public ProductUpdateException(String message) {
        super(message);
    }

    public ProductUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
