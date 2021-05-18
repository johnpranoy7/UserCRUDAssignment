package com.jyalla.demo.messaging;

public class MessageNotSentException extends RuntimeException {

    public MessageNotSentException() {
        super();
    }

    public MessageNotSentException(String message) {
        super(message);
    }


}
