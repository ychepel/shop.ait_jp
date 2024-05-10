package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
    }

    public EmptyCartException(String message) {
        super(message);
    }

    public EmptyCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
