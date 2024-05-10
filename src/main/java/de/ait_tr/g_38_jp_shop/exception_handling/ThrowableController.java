package de.ait_tr.g_38_jp_shop.exception_handling;

import de.ait_tr.g_38_jp_shop.exception_handling.exception.CustomerNotFoundException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.CustomerSaveException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.ProductSaveException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.ProductUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ThrowableController {

    Logger logger = LoggerFactory.getLogger(ThrowableController.class);

    @ExceptionHandler(ProductUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Response handleException(ProductUpdateException e) {
        logger.warn(e.getMessage(), e);
        return getResponseWithDetails(e);
    }

    @ExceptionHandler(ProductSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Response handleException(ProductSaveException e) {
        logger.warn(e.getMessage(), e);
        return getResponseWithDetails(e);
    }

    @ExceptionHandler(CustomerSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Response handleException(CustomerSaveException e) {
        logger.warn(e.getMessage(), e);
        return getResponseWithDetails(e);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Response handleException(CustomerNotFoundException e) {
        return getResponseWithDetails(e);
    }

    private Response getResponseWithDetails(Exception e) {
        Throwable causeException = e.getCause();
        String additionalMessage = causeException == null ? null : parseExceptionMessage(causeException.getMessage());
        return new Response(e.getMessage(), additionalMessage);
    }

    private String parseExceptionMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("messageTemplate='([^']*)'");
        Matcher matcher = pattern.matcher(errorMessage);
        return matcher.find() ? matcher.group(1) : "something went wrong";
    }
}
