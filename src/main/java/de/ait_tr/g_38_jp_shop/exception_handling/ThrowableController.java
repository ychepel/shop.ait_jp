package de.ait_tr.g_38_jp_shop.exception_handling;

import de.ait_tr.g_38_jp_shop.exception_handling.exception.ProductUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ThrowableController {

    Logger logger = LoggerFactory.getLogger(ThrowableController.class);

    @ExceptionHandler(ProductUpdateException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    default Response handleException(ProductUpdateException e) {
        logger.warn(e.getMessage(), e);
        return new Response(e.getMessage());
    }
}
