package de.ait_tr.g_38_jp_shop.exception_handling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String productTitle) {
        super(String.format("Product `%s` not found", productTitle));
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
