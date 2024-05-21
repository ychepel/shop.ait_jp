package de.ait_tr.g_38_jp_shop.exception_handling;

import de.ait_tr.g_38_jp_shop.exception_handling.exception.EmptyCartException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.InvalidRequestException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.ProductNotFoundException;
import de.ait_tr.g_38_jp_shop.exception_handling.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Response> handleException(InvalidRequestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Response> handleException(EmptyCartException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(UserNotFoundException e) {
        logger.warn(e.getMessage());
        Response response = new Response("Provided data is invalid");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
