package com.kicinger.hsbc.controllers.advices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krzysztofkicinger on 19/05/2017.
 */
@ControllerAdvice
public class UsersControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Map<String, Object>> handleValidationException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(convertErrorMessageToMap(ex.getMessage()));
    }

    private Map<String, Object> convertErrorMessageToMap(String message) {
        return Collections.singletonMap("error", message);
    }

}
