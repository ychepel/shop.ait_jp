package de.ait_tr.g_38_jp_shop.exception_handling.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
