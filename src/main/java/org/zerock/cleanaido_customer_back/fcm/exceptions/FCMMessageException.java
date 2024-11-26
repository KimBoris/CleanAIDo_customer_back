package org.zerock.cleanaido_customer_back.fcm.exceptions;

public class FCMMessageException extends RuntimeException {

    public FCMMessageException(String message) {
        super(message);
    }
}