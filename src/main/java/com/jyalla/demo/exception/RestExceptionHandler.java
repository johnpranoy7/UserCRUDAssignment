package com.jyalla.demo.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @Order(Ordered.HIGHEST_PRECEDENCE)
// @ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // @Override
    // protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
    // HttpHeaders headers, HttpStatus status, WebRequest request) {
    // // TODO Auto-generated method stub
    // return ResponseEntity.ok(new ErrorDTO(status.value(), ex.getLocalizedMessage()));
    // }

    private String getCustomMessage(int status) {
        switch (status) {
            case 500:
                return "INTERNAL EXCEPTION";
            default:
                return "";
        }
    }

}
