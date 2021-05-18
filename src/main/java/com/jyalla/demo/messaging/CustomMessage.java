package com.jyalla.demo.messaging;

import java.util.Date;


public class CustomMessage {
    private String messageId;
    private String messageBody;
    private Date messageDate;

    public CustomMessage(String messageId, String messageBody, Date messageDate) {
        super();
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.messageDate = messageDate;
    }

    public CustomMessage() {
        super();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return "CustomMessage [messageId=" + messageId + ", messageBody=" + messageBody + ", messageDate=" + messageDate + "]";
    }

}
