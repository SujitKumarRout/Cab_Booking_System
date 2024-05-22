package com.sujit.CabBooking.Exception;

public class NoAvailableDriverException extends RuntimeException {
    public NoAvailableDriverException(String message) {
        super(message);
    }
}
