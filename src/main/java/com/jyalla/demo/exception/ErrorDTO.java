package com.jyalla.demo.exception;

public class ErrorDTO {
    int statusCode;
    String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDTO(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "ErrorDTO [statusCode=" + statusCode + ", message=" + message + "]";
    }

}
