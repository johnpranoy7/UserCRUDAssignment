package com.jyalla.demo.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Order(Ordered.HIGHEST_PRECEDENCE)
// @SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        var errorDto = new ErrorDTO("User Not Found", details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> articleNotFound(ArticleNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        var errorDto = new ErrorDTO("Article Not Found", details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class, UnsupportedJwtException.class, BadCredentialsException.class})
    public ResponseEntity<Object> unauthorisedRequest(BadCredentialsException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        var errorDto = new ErrorDTO(e.getLocalizedMessage(), details, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<Object> tokenExpiredException(ExpiredJwtException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        var errorDto = new ErrorDTO(e.getLocalizedMessage(), details, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        var errorDto = new ErrorDTO("methodArgumentTypeMismatchException Occured...", details, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult()
                .getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult()
                .getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        var errorDto = new ErrorDTO(ex.getLocalizedMessage(), errors, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, errorDto, headers, errorDto.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        var errorDto = new ErrorDTO(ex.getLocalizedMessage(), List.of(error), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ErrorDTO("Server Error", details, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
