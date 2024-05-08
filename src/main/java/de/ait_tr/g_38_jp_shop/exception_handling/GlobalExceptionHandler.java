package de.ait_tr.g_38_jp_shop.exception_handling;

import de.ait_tr.g_38_jp_shop.exception_handling.exception.ProductSaveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductSaveException.class)
    public ResponseEntity<Response> handleException(ProductSaveException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
