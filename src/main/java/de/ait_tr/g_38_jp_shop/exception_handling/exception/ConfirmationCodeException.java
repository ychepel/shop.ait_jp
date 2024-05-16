package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class ConfirmationCodeException extends Exception {

    public ConfirmationCodeException() {
    }

    public ConfirmationCodeException(String message) {
        super(message);
    }

    public ConfirmationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
