package com.jyalla.demo.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Order(Ordered.HIGHEST_PRECEDENCE)
// @SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class RestExceptionHandler // extends ResponseEntityExceptionHandler {
{
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDTO errorDto = new ErrorDTO("User Not Found", details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> articleNotFound(ArticleNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDTO errorDto = new ErrorDTO("Article Not Found", details, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDTO errorDto = new ErrorDTO("unable to Convert...", details, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale loc) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorDTO errorDto = new ErrorDTO("unable to Convert...", details, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /*
     * @Override protected ModelAndView
     * handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest
     * request, HttpServletResponse response,
     * 
     * @Nullable Object handler) throws IOException { System.out.println(ex);
     * response.sendError(HttpServletResponse.SC_BAD_REQUEST); return new ModelAndView(); }
     */


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDTO error = new ErrorDTO("Server Error", details, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
