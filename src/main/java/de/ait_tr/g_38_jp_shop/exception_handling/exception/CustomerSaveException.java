package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class CustomerSaveException extends RuntimeException {

    public CustomerSaveException() {
    }

    public CustomerSaveException(String message) {
        super(message);
    }

    public CustomerSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
