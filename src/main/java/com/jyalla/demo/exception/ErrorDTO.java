package com.jyalla.demo.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ErrorDTO {

    HttpStatus status;
    String message;
    List<String> details;

    public ErrorDTO(String message, List<String> details, HttpStatus status) {
        super();
        this.message = message;
        this.details = details;
        this.status = status;
    }

    public ErrorDTO() {
        super();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorDTO [status=" + status + ", message=" + message + ", details=" + details + "]";
    }



}
