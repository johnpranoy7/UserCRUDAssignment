package com.jyalla.demo.exception;

import java.util.List;

public class ErrorDTO {

    int status;
    String message;
    List<String> details;

    public ErrorDTO(String message, List<String> details, int status) {
        super();
        this.message = message;
        this.details = details;
        this.status = status;
    }

    public ErrorDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
